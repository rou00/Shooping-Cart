package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.entity.CartItem;
import com.rou00.shopcart.service.Cart.CartItemService;
import com.rou00.shopcart.service.Cart.Impl.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartServiceImpl cartService;

    @PostMapping("/item/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestParam (required = false) Long cartId, @RequestParam Long productId, @RequestParam Integer quantity){
        try {
            if(cartId == null){
                  cartId = cartService.initilizeNewCart();
            }
            cartItemService.addItemToCart(cartId,productId,quantity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<CartItem> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<CartItem> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam Integer itemId){
        try {
            cartItemService.updateItemQuantity(cartId,productId,itemId);
            return new ResponseEntity<>(null,HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
