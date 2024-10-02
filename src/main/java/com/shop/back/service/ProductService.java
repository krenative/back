package com.shop.back.service;

import com.shop.back.dto.ProductDto;
import com.shop.back.exception.InvalidRequestException;
import com.shop.back.exception.ProductNotFoundException;
import com.shop.back.model.Product;
import com.shop.back.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.shop.back.mapper.ProductMapper.INSTANCE;
import static java.time.LocalDateTime.now;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private static final String NOT_FOUND = "Product not found with id: ";

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductDto createProduct(ProductDto productDto) {
        log.info("creating product: {}",productDto);
        Product product = Optional.ofNullable(productDto)
                .map(INSTANCE::productDtoToProduct)
                .orElseThrow(() -> new InvalidRequestException("Invalid product request"));

        product.setCreatedAt(now());
        repository.save(product);
        return productDto;
    }

    @Cacheable(value = "products")
    public List<ProductDto> getAllProducts() {
        log.info("retrieving all products");
        return repository.findAll().stream()
                .map(INSTANCE::productToProductDto)
                .toList();
    }

    @Cacheable(value = "product", key = "#id")
    public ProductDto getProductById(Long id) {
        log.info("retrieving product by ID : {}",id);
        return repository.findById(id)
                .map(INSTANCE::productToProductDto)
                .orElseThrow(() -> new ProductNotFoundException(NOT_FOUND + id));
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        log.info("updating product by ID : {}",id);
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(NOT_FOUND + id));

        populateProduct(productDto, product);

        product.setUpdatedAt(now());
        repository.save(product);

        return INSTANCE.productToProductDto(product);
    }

    @Transactional
    @CacheEvict(value = "products",allEntries = true)
    public void deleteProduct(Long id) {
        log.info("deleting product by ID : {}",id);
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(NOT_FOUND + id);
        }
        repository.deleteById(id);
    }

    private void populateProduct(ProductDto productDto, Product product) {
        if (productDto.getCode() != null) product.setCode(productDto.getCode());
        if (productDto.getName() != null) product.setName(productDto.getName());
        if (productDto.getDescription() != null) product.setDescription(productDto.getDescription());
        if (productDto.getImage() != null) product.setImage(productDto.getImage());
        if (productDto.getCategory() != null) product.setCategory(productDto.getCategory());
        if (productDto.getPrice() != 0) product.setPrice(productDto.getPrice());
        if (productDto.getQuantity() != 0) product.setQuantity(productDto.getQuantity());
        if (productDto.getInternalReference() != null) product.setInternalReference(productDto.getInternalReference());
        if (productDto.getShellId() != null) product.setShellId(productDto.getShellId());
        if (productDto.getRating() != 0) product.setRating(productDto.getRating());
        if (productDto.getInventoryStatus() != null) product.setInventoryStatus(productDto.getInventoryStatus());
    }
}
