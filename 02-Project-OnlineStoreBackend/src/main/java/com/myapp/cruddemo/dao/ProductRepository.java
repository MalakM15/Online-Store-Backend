package com.myapp.cruddemo.dao;

import com.myapp.cruddemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryId(int categoryId);

}