package com.rou00.shopcart.repository;

import com.rou00.shopcart.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository  extends JpaRepository<CartItem,Long> {

    void deleteAllByCartId(Long id);
}
