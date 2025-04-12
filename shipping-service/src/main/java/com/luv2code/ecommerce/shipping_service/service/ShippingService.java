package com.luv2code.ecommerce.shipping_service.service;

import com.luv2code.ecommerce.shipping_service.dto.ShippingResponseDto;
import com.luv2code.ecommerce.shipping_service.entity.Shipping;
import com.luv2code.ecommerce.shipping_service.entity.ShippingStatus;
import com.luv2code.ecommerce.shipping_service.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;
    private final ModelMapper modelMapper;


    public ShippingResponseDto confirmShipping(Long orderID) {
        log.info("Confirming shipping for order: {}", orderID);

        // Create a new Shipping entity and set initial values
        Shipping shipping = new Shipping();
        shipping.setOrderId(orderID);
        shipping.setShippingStatus(ShippingStatus.PENDING);
        shipping.setTrackingNumber(UUID.randomUUID().toString());

        Shipping savedShipping = shippingRepository.save(shipping);

        // Simulate shipping status update to SHIPPED
        savedShipping.setShippingStatus(ShippingStatus.SHIPPED);
        Shipping updatedShipping = shippingRepository.save(savedShipping);

        return modelMapper.map(updatedShipping, ShippingResponseDto.class);
    }

    public String cancelShipping(Long orderId) {

        log.info("Cancel shipping for order: {}", orderId);

        Shipping shipping = shippingRepository.findByOrderId(orderId);
        shipping.setShippingStatus(ShippingStatus.CANCELLED);

        Shipping savedShipping = shippingRepository.save(shipping);

        return "Shipment for Order with ID: " + orderId + " has been cancelled successfully, and stocks are restored.";
    }
}
