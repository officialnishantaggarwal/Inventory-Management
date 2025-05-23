package com.luv2code.ecommerce.order_service.service;

import com.luv2code.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.luv2code.ecommerce.order_service.clients.ShippingOpenFeignClient;
import com.luv2code.ecommerce.order_service.dto.OrderRequestDto;
import com.luv2code.ecommerce.order_service.dto.ShippingResponseDto;
import com.luv2code.ecommerce.order_service.entity.OrderItem;
import com.luv2code.ecommerce.order_service.entity.OrderStatus;
import com.luv2code.ecommerce.order_service.entity.Orders;
import com.luv2code.ecommerce.order_service.repository.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;
    private final ShippingOpenFeignClient shippingOpenFeignClient;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all Orders");
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with id: {}", id);
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return modelMapper.map(orders, OrderRequestDto.class);
    }

//    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker",fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Calling the createOrder method");
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);
        Orders orders = modelMapper.map(orderRequestDto, Orders.class);
        for (OrderItem orderItem : orders.getItems()) {
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrder = ordersRepository.save(orders);

        // Confirm shipping via shipping service
        ShippingResponseDto shipping = shippingOpenFeignClient.confirmShipping(savedOrder.getId());
        log.info("Shipping confirmed: {}", shipping);
        savedOrder.setOrderStatus(OrderStatus.SHIPPED);
        Orders shippedOrder = ordersRepository.save(savedOrder);

        return modelMapper.map(shippedOrder, OrderRequestDto.class);
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable) {
        log.error("Fallback Occurred due to : {}", throwable.getMessage());

        return new OrderRequestDto();
    }

    @Transactional
    public String cancelOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        if (order.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        }

        // Call Inventory Service to restore stocks
        inventoryOpenFeignClient.restoreStocks(modelMapper.map(order, OrderRequestDto.class));

        // Update order status after cancelling
        order.setOrderStatus(OrderStatus.CANCELLED);
        Orders savedOrder = ordersRepository.save(order);

        // Cancel shipping via shipping service
        String shippingCancelled = shippingOpenFeignClient.cancelShipping(savedOrder.getId());
        log.info("Shipping cancelled: {}", shippingCancelled);
        return shippingCancelled;
    }
}
