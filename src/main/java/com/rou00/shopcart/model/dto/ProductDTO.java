package com.rou00.shopcart.model.dto;

import com.rou00.shopcart.model.entity.Category;
import lombok.Data;


import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory; // Quantity
    private String description;


    private Category category;

    private List<ImageDTO> images;

    public ProductDTO(String name, String brand, BigDecimal price, int inventory, String description, Category category) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
