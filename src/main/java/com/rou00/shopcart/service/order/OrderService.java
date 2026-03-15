package com.rou00.shopcart.service.order;

import com.rou00.shopcart.model.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
