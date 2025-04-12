package com.luv2code.ecommerce.order_service.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ShippingResponseDto {
    private Long id;

    private Long orderID;

    private ShippingStatus shippingStatus;

    private String trackingNumber;
}
