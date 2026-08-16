package com.myapp.cruddemo.dao;

import com.myapp.cruddemo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
