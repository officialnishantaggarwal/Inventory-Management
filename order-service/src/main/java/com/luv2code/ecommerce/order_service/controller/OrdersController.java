package com.luv2code.ecommerce.order_service.controller;

import com.luv2code.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.luv2code.ecommerce.order_service.config.FeaturesEnableConfig;
import com.luv2code.ecommerce.order_service.dto.OrderRequestDto;
import com.luv2code.ecommerce.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
@RefreshScope
public class OrdersController {

    private final OrdersService ordersService;

    @Value("${my.variable}")
    private String myVariable;

    private final FeaturesEnableConfig featuresEnableConfig;

    @GetMapping("/helloOrders")
    public String helloOrders(@RequestHeader("X-User-Id") Long userId){
        if(featuresEnableConfig.isUserTrackingEnabled()){
            return "User Tacking Enabled wooo, Hello from Orders Service, user id is: "+userId + "github: "+myVariable;
        }
        else{
            return "User Tacking Disabled ahhh, Hello from Orders Service, user id is: "+userId + "github: "+myVariable;
        }
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok(ordersService.createOrder(orderRequestDto));
    }

    @PutMapping("/cancel-order/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        String responseMessage = ordersService.cancelOrder(orderId);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(){
        log.info("Fetching all orders by controller");
        List<OrderRequestDto> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id){
        log.info("Fetching order with id: {}",id);
        OrderRequestDto order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
