package com.luv2code.ecommerce.shipping_service.controller;

import com.luv2code.ecommerce.shipping_service.dto.ShippingResponseDto;
import com.luv2code.ecommerce.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class ShippingController {
    private final ShippingService shippingService;

    @PostMapping("/confirm/{orderId}")
    public ResponseEntity<ShippingResponseDto> confirmShipping(@PathVariable Long orderId) {
        ShippingResponseDto responseDTO = shippingService.confirmShipping(orderId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<String> cancelShipping(@PathVariable Long orderId) {
        String cancelledShipping = shippingService.cancelShipping(orderId);
        return ResponseEntity.ok(cancelledShipping);
    }
}
