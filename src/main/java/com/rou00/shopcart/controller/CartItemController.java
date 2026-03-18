package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.entity.Cart;
import com.rou00.shopcart.model.entity.CartItem;
import com.rou00.shopcart.model.entity.User;
import com.rou00.shopcart.service.Cart.CartItemService;
import com.rou00.shopcart.service.Cart.Impl.CartServiceImpl;
import com.rou00.shopcart.service.User.UserService;
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
    private final UserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestParam Long productId, @RequestParam Integer quantity,@RequestParam Long userId){
        try {
                  User user = userService.getUserById(userId);
                  Cart cart = cartService.initilizeNewCart(user);

            cartItemService.addItemToCart(cart.getId(),productId,quantity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
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
