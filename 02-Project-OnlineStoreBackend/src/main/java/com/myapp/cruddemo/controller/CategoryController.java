package com.myapp.cruddemo.controller;

import com.myapp.cruddemo.entity.Category;
import com.myapp.cruddemo.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id) {
        return categoryService.getCategory(id);
    }

    // POST /api/categories
    // ADMIN only
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category) {

        Category savedCategory = categoryService.createCategory(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCategory);
    }

    // PUT /api/categories/{id}
    // ADMIN only
    @PutMapping("/{id}")
    public Category updateCategory(
            @PathVariable int id,
            @RequestBody Category category) {

        return categoryService.updateCategory(id, category);
    }

    // DELETE /api/categories/{id}
    // ADMIN only
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
