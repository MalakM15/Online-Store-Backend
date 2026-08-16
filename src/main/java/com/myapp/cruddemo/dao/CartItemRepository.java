package com.myapp.cruddemo.dao;

import com.myapp.cruddemo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    Optional<CartItem> findByCartIdAndProductId(int cartId ,int productId);

}
