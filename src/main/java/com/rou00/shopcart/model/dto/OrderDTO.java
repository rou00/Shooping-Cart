package com.rou00.shopcart.model.dto;

import com.rou00.shopcart.enums.OrderStatus;
import com.rou00.shopcart.model.entity.OrderItem;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OrderDTO {

    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDTO> Items;

}
