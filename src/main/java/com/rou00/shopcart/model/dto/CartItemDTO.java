package com.rou00.shopcart.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {

    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;

}
