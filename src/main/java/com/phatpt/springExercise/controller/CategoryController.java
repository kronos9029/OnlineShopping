package com.phatpt.springExercise.controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.entity.Category;
import com.phatpt.springExercise.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{cateId}")
    public Category getProductById(@PathVariable(value = "cateId") Long cateId) {
        return categoryService.getCateById(cateId);
    }

    @PostMapping("")
    public Category createCate(@RequestBody Category newCate) {
        return categoryService.createCate(newCate);
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category cateDetail,
            @PathVariable(value = "cateId") Long cateId) {
        return categoryService.updateCategory(cateDetail, cateId);
    }

    @DeleteMapping("/{cateId}")
    public Map<String, Boolean> deleteCate(@PathVariable(value = "cateId") Long cateId) {
        return categoryService.deleteCate(cateId);
    }

}
