package com.luv2code.ecommerce.inventory_service.service;

import com.luv2code.ecommerce.inventory_service.dto.ProductDto;
import com.luv2code.ecommerce.inventory_service.entity.Product;
import com.luv2code.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    public List<ProductDto> getAllInventory(){
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product -> mapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto getProductById(Long id){
        log.info("Fetching Product with ID: {}",id);
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Inventory not Found"));
        return mapper.map(product, ProductDto.class);
    }
}
