package com.rou00.shopcart.service.Cart.Impl;

import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.entity.Cart;
import com.rou00.shopcart.repository.CartItemRepository;
import com.rou00.shopcart.repository.CartRepository;
import com.rou00.shopcart.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(long id) {
        Cart cart = getCart(id);
        return  cart.getTotalAmount();
    }

    @Override
    public Long initilizeNewCart(){
        Cart newCart = new Cart();
         //Long newCartId = cartIdGenerator.incrementAndGet();
        //newCart.setId(newCartId);
        return  cartRepository.save(newCart).getId();
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
