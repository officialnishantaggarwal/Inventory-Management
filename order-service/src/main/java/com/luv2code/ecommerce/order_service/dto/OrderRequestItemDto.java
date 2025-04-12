package com.luv2code.ecommerce.order_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestItemDto {

    private Long id;
    private Long productId;
    private Integer quantity;

}
