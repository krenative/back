package com.shop.back.model;

import com.shop.back.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private Long shellId;
    private int rating;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
