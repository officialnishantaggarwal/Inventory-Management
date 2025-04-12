package com.luv2code.ecommerce.order_service.dto;

import lombok.Data;

@Data
public class ShippingResponseDto {
    private Long id;

    private Long orderID;

    private ShippingStatus shippingStatus;

    private String trackingNumber;
}
