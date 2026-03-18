package com.rou00.shopcart.service.order;

import com.rou00.shopcart.model.dto.OrderDTO;
import com.rou00.shopcart.model.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);

    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getAllOrderMadeByUsers();

    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDTO(Order order);
}
