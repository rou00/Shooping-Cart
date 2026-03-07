package com.rou00.shopcart.model.dto;

import com.rou00.shopcart.model.entity.Product;
import lombok.Data;


import java.util.List;

@Data
public class CategoryDTO {

    private Long id;
    private String name;

    private List<Product> products;

    public CategoryDTO(String name) {
        this.name = name;
    }

}
