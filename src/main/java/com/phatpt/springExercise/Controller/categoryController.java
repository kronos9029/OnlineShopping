package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Get All Category
    @GetMapping("/")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    //Get Cate By ID
    @GetMapping("/{id}")
    public ResponseEntity<Category> getProductById(@PathVariable(value = "id") Long cateId) {
            return categoryService.getProductById(cateId);
    }

    //Save Cate
    @PostMapping("/")
    public Category createCate(@RequestBody Category newCate){
        return categoryService.createCate(newCate);
    }

    //Update Cate
    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category cateDetail, @PathVariable(value = "id") Long cateId){
        return categoryService.updateCategory(cateDetail, cateId);
    }

    //Delete Product
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteCate(@PathVariable(value = "id") Long cateId){
        return categoryService.deleteCate(cateId);
    }

}
