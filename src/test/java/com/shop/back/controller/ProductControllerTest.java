package com.shop.back.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.back.dto.ProductDto;
import com.shop.back.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;

    @BeforeEach
    public void setup() {
        productDto = new ProductDto();
        productDto.setCode("code");
        productDto.setShellId(1L);
        productDto.setName("Test Product");
        productDto.setPrice(10.99);

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() throws Exception {
        when(productService.createProduct(any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDto> products = Arrays.asList(productDto);
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(productDto.getName()));
    }

    @Test
    void testGetProductById() throws Exception {
        when(productService.getProductById(productDto.getShellId())).thenReturn(productDto);

        mockMvc.perform(get("/api/products/{id}", productDto.getShellId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()));
    }

    @Test
    void testUpdateProduct() throws Exception {
        when(productService.updateProduct(eq(productDto.getShellId()), any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(patch("/api/products/{id}", productDto.getShellId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", productDto.getShellId()))
                .andExpect(status().isOk());
    }
}
