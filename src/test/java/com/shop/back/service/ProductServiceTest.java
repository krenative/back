package com.shop.back.service;

import com.shop.back.dto.ProductDto;
import com.shop.back.exception.InvalidRequestException;
import com.shop.back.exception.ProductNotFoundException;
import com.shop.back.model.Product;
import com.shop.back.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private ProductDto productDto;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setId(1L);
        product.setCode("code");
        product.setName("name");
        product.setPrice(199);

        productDto = new ProductDto();
        productDto.setShellId(1L);
        productDto.setCode("code");
        productDto.setName("name");
        productDto.setPrice(199);
    }

    @Test
    void createProduct_validDto_returnsDto() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto createdProduct = productService.createProduct(productDto);
        assertThat(createdProduct).isNotNull();
        assertEquals(createdProduct.getName(), product.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void createProduct_nullDto_throwsInvalidRequestException() {
        assertThrows(InvalidRequestException.class, () -> productService.createProduct(null));
    }

    @Test
    void getAllProducts_returnsProductList() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<ProductDto> products = productService.getAllProducts();
        assertThat(products).isNotEmpty();
        assertEquals(products.size(),1);
        verify(productRepository).findAll();
    }

    @Test
    void getProductById_existingId_returnsProductDto() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        ProductDto foundProduct = productService.getProductById(1L);
        assertThat(foundProduct).isNotNull();
        assertThat(foundProduct.getShellId()).isEqualTo(product.getShellId());
        verify(productRepository).findById(any(Long.class));

    }

    @Test
    void getProductById_nonExistingId_throwsProductNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void updateProduct_existingId_updatesAndReturnsProduct() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto updatedProduct = productService.updateProduct(1L, productDto);
        assertThat(updatedProduct).isNotNull();
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_nonExistingId_throwsProductNotFoundException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, productDto));
    }

    @Test
    void deleteProduct_existingId_performsDeletion() {
        when(productRepository.existsById(anyLong())).thenReturn(true);
        productService.deleteProduct(1L);
        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_nonExistingId_throwsProductNotFoundException() {
        when(productRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(1L));
    }

}
