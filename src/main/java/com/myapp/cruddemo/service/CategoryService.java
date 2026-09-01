package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.CategoryRepository;
import com.myapp.cruddemo.entity.Category;
import com.myapp.cruddemo.exception.*;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryCacheService categoryCacheService;

    public CategoryService(CategoryRepository categoryRepository, CategoryCacheService categoryCacheService) {
        this.categoryRepository = categoryRepository;
        this.categoryCacheService = categoryCacheService;

    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    //@Cacheable(value = "categories", key="#id")
    public Category getCategory(int id) {

        //System.out.println("  DATABASE: Loading category " + id);

        return categoryCacheService.findCategory(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @CachePut(value = "categories", key = "#id")
    public Category updateCategory(int id, Category updatedCategory) {
        System.out.println("  UPDATE: database + cache");

        Category existingCategory = getCategory(id);

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(existingCategory);
    }

    @CacheEvict(value= "categories", key= "#id")
    public void deleteCategory(int id) {

        Category category = getCategory(id);
        if (category.getProducts() != null && !category.getProducts().isEmpty() ){
                            throw new BadRequestException("Cannot delete category( "+category.getName()+") it has products.");

        }
        categoryRepository.delete(category);
    }
}