package com.rou00.shopcart.service.Cart.Impl;

import com.rou00.shopcart.exceptions.ResourceNotFound;
import com.rou00.shopcart.model.entity.Cart;
import com.rou00.shopcart.model.entity.CartItem;
import com.rou00.shopcart.model.entity.Product;
import com.rou00.shopcart.repository.CartItemRepository;
import com.rou00.shopcart.repository.CartRepository;
import com.rou00.shopcart.service.Cart.CartItemService;
import com.rou00.shopcart.service.Cart.CartService;
import com.rou00.shopcart.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                                .filter(item -> item.getProduct().getId().equals(productId))
                                .findFirst().orElse(new CartItem());

        if(product.getInventory() >= quantity) {
            if (cartItem.getId() == null) {
                // check if the product exist
                cartItem.setCart(cart);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(product.getPrice());
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
            }
            cartItem.setTotalPtice();
            cart.addItem(cartItem);
            cartItemRepository.save(cartItem);
            cartRepository.save(cart);
        }else{
            throw new ResourceNotFound("the Quantity Requested is to much to ask for");
        }

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = cart.getCartItems()
                .stream().filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()-> new ResourceNotFound("Item not Found to remove it from cart"));
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPtice();
                });
        BigDecimal totalAmount = cart.getCartItems().stream()
                        .map(CartItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO,BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
}
