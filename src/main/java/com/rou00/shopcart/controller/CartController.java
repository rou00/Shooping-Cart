package com.rou00.shopcart.controller;

import com.rou00.shopcart.model.dto.CartDTO;
import com.rou00.shopcart.model.entity.Cart;
import com.rou00.shopcart.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping("/getCart/{cartId}")
    public ResponseEntity<Cart>  getCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getCart(cartId);
            return new ResponseEntity<>(cart, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/clearCart/{cartId}")
    public ResponseEntity<Cart>  deleteCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart/{cartId}/totalPrice")
    public ResponseEntity<BigDecimal> getTotalAmount(@PathVariable Long cartId){
        try {
            BigDecimal totlaPrice = cartService.getTotalPrice(cartId);
            return new ResponseEntity<>(totlaPrice, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
