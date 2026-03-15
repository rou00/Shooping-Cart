package com.rou00.shopcart.service.Cart;

import com.rou00.shopcart.model.entity.CartItem;

public interface CartItemService {

    void addItemToCart(Long cartId, Long productId, int quantity );
    void removeItemFromCart(Long cartId, Long productId );
    void updateItemQuantity(Long cartId, Long productId, int quantity);
}
