package com.myapp.cruddemo.service;

import com.myapp.cruddemo.dao.CategoryRepository;
import com.myapp.cruddemo.entity.Category;
import com.myapp.cruddemo.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(int id) {
        return categoryRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + id)
        );
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(int id, Category updatedCategory) {

        Category existingCategory = getCategory(id);

        existingCategory.setName(updatedCategory.getName());
        existingCategory.setDescription(updatedCategory.getDescription());

        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int id) {

        Category category = getCategory(id);

        categoryRepository.delete(category);
    }
}