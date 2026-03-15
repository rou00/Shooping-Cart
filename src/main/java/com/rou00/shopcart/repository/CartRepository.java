package com.rou00.shopcart.repository;

import com.rou00.shopcart.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long userId);
}
