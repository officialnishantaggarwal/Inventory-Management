package com.luv2code.ecommerce.shipping_service.repository;

import com.luv2code.ecommerce.shipping_service.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping,Long> {
    Optional<Shipping> findByOrderId(Long orderId);
}
