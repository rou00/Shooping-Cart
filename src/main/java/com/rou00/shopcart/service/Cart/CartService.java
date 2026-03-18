package com.rou00.shopcart.service.Cart;

import com.rou00.shopcart.model.entity.Cart;
import com.rou00.shopcart.model.entity.User;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(long id);

    Cart initilizeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
