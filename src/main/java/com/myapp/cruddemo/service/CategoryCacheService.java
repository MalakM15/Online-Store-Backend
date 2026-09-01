package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.CategoryRepository;
import com.myapp.cruddemo.entity.Category;

import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;
@Service
public class CategoryCacheService {

    private final CategoryRepository categoryRepository;

    public CategoryCacheService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Cacheable(value = "categories", key = "#id")
    public Optional<Category> findCategory(int id) {
        System.out.println("DATABASE: Loading category " + id);
        return categoryRepository.findById(id);
    }
}
