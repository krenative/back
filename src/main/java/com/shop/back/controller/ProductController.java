package com.shop.back.controller;

import com.shop.back.dto.ProductDto;
import com.shop.back.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController implements ProductApi {
    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(service.createProduct(productDto));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {

        return ResponseEntity.ok(service.getAllProducts());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PatchMapping("/{id}")
    public ProductDto updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductDto productDto) {
        return service.updateProduct(id, productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        service.deleteProduct(id);
    }
}
