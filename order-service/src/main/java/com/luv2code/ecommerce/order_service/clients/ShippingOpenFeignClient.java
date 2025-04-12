package com.luv2code.ecommerce.order_service.clients;

import com.luv2code.ecommerce.order_service.dto.ShippingResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "Shipping-Service", path = "/shipping")
public interface ShippingOpenFeignClient {

    @PostMapping("/status/confirm/{orderId}")
    ShippingResponseDto confirmShipping(@PathVariable("orderId") Long orderId);

    @PostMapping("/status/cancel/{orderId}")
    String cancelShipping(@PathVariable("orderId") Long orderId);

}
