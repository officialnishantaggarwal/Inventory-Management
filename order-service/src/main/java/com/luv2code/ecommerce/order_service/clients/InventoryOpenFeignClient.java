package com.luv2code.ecommerce.order_service.clients;

import com.luv2code.ecommerce.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/inventory",url = "${INVENTORY_SERVICE_URI:}")
public interface InventoryOpenFeignClient {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);

    @PutMapping("/products/restore-stocks")
    String restoreStocks(@RequestBody OrderRequestDto orderRequestDTO);
}
