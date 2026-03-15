package com.rou00.shopcart.service.Cart;

import com.rou00.shopcart.model.entity.Cart;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(long id);

    Long initilizeNewCart();

    Cart getCartByUserId(Long userId);
}
