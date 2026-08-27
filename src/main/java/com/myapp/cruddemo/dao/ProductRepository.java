package com.myapp.cruddemo.dao;

import com.myapp.cruddemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryId(int categoryId);
    
    @Modifying
    @Query("""
        UPDATE Product p
        SET p.stock = p.stock - :quantity
        WHERE p.id = :productId
        AND p.stock >= :quantity
        """)
    int decreaseStockIfAvailable(
            @Param("productId") int productId,
            @Param("quantity") int quantity
    );

}