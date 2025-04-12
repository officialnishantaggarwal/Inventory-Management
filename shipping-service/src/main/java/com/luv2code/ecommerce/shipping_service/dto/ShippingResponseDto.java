package com.luv2code.ecommerce.shipping_service.dto;

import com.luv2code.ecommerce.shipping_service.entity.ShippingStatus;
import lombok.Data;

@Data
public class ShippingResponseDto {
    private Long id;

    private Long orderID;

    private ShippingStatus shippingStatus;

    private String trackingNumber;
}
