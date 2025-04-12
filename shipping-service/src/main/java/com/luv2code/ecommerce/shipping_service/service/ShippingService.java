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
        shipping.setOrderID(orderID);
        shipping.setShippingStatus(ShippingStatus.PENDING);
        shipping.setTrackingNumber(UUID.randomUUID().toString());

        // Save the initial shipping entity
        Shipping savedShipping = shippingRepository.save(shipping);

        // Simulate shipping status update to SHIPPED
        savedShipping.setShippingStatus(ShippingStatus.SHIPPED);
        Shipping updatedShipping = shippingRepository.save(savedShipping);

        // Map Shipping entity to ShippingResponseDTO before returning
        return modelMapper.map(updatedShipping, ShippingResponseDto.class);
    }

    public String cancelShipping(Long orderId) {

        log.info("Cancel shipping for order: {}", orderId);

        // Create a new Shipping entity and set initial values
        Shipping shipping = shippingRepository.findByOrderId(orderId)
                .orElseThrow(()-> new RuntimeException("Shipment for Order ID not found"));
        shipping.setShippingStatus(ShippingStatus.CANCELLED);

        // Save the initial shipping entity
        Shipping savedShipping = shippingRepository.save(shipping);

        return "Shipment for Order with ID: " + orderId + " has been cancelled successfully, and stocks are restored.";
    }
}
