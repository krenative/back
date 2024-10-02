package com.shop.back.dto;

import com.shop.back.enums.InventoryStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ProductDto {

    @NotBlank(message = "Product code must not be blank")
    private String code;

    @Length(min = 2, max = 20, message = "Length between 2 and 20")
    private String name;

    @Length(max = 256, message = "Max length is 256")
    private String description;

    private String image;
    private String category;

    @Min(value = 0, message = "Min price is 0")
    private double price;

    @Min(value = 0, message = "Min price is 0")
    private int quantity;

    private String internalReference;
    private Long shellId;
    private int rating;

    @Enumerated(EnumType.STRING)
    private InventoryStatus inventoryStatus;

}
