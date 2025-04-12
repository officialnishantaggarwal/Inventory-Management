package com.luv2code.ecommerce.order_service.service;

import com.luv2code.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.luv2code.ecommerce.order_service.dto.OrderRequestDto;
import com.luv2code.ecommerce.order_service.entity.OrderItem;
import com.luv2code.ecommerce.order_service.entity.OrderStatus;
import com.luv2code.ecommerce.order_service.entity.Orders;
import com.luv2code.ecommerce.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public List<OrderRequestDto> getAllOrders(){
        log.info("Fetching all Orders");
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order,OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching order with id: {}",id);
        Orders orders = ordersRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Order not found with id: "+id));
        return modelMapper.map(orders,OrderRequestDto.class);
    }

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);
        Orders orders = modelMapper.map(orderRequestDto,Orders.class);
        for (OrderItem orderItem : orders.getItems()){
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrder = ordersRepository.save(orders);

        return modelMapper.map(savedOrder,OrderRequestDto.class);
    }
}
