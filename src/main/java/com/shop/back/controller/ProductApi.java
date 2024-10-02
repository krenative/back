package com.shop.back.controller;

import com.shop.back.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Product", description = "The Product API")
public interface ProductApi {

    @Operation(summary = "Create a new product", responses = {
            @ApiResponse(description = "The created product", responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class),
                            examples = @ExampleObject(value = "{\"code\": \"1\",\"name\": \"Example Product\", \"description\": \"Description here\", \"price\": 19.99, \"quantity\": 100}")))
    })
    ResponseEntity<ProductDto> createProduct(ProductDto productDto);

    @Operation(summary = "Get all products")
    ResponseEntity<List<ProductDto>> getAllProducts();

    @Operation(summary = "Get a product by ID", responses = {
            @ApiResponse(description = "The retrieved product", responseCode = "200")
    })
    ResponseEntity<ProductDto> getProductById(Long id);

    @Operation(summary = "Update product by ID", responses = {
            @ApiResponse(description = "The updated product", responseCode = "200",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class),
                            examples = @ExampleObject(value = "{\"code\": \"1\",\"name\": \"Updated Product\", \"description\": \"Updated description here\", \"price\": 29.99, \"quantity\": 50}")))
    })
    ProductDto updateProduct(Long id, ProductDto productDto);

    @Operation(summary = "Delete a product by its ID")
    void deleteProduct(Long id);
}