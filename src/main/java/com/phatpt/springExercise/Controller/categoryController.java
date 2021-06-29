package com.phatpt.springExercise.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Exception.CategoryNotFoundException;
import com.phatpt.springExercise.Repository.categoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class categoryController {
    
    @Autowired
    private categoryRepository categoryRepository;

    //Get All Category
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

    //Get Cate By ID
    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getProductById(@PathVariable(value = "id") Long cateId) 
            throws CategoryNotFoundException{
        Category category = categoryRepository.findById(cateId)
                                        .orElseThrow(() -> new CategoryNotFoundException(cateId));
        
        return ResponseEntity.ok().body(category);
    }

    //Save Cate
    @PostMapping("/categories")
    public Category createCate(@RequestBody Category newCate){
        return this.categoryRepository.save(newCate);
    }

    //Update Cate
    @PutMapping("/categories/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category cateDetail, @PathVariable(value = "id") Long cateId){
        Category category = categoryRepository.findById(cateId)
                                        .orElseThrow(() -> new CategoryNotFoundException(cateId));
        
        category.setCateName(cateDetail.getCateName());
        category.setCateDescription(cateDetail.getCateDescription());

        return ResponseEntity.ok(this.categoryRepository.save(category));
    }

    //Delete Product
    @DeleteMapping("/categories/{id}")
    public Map<String, Boolean> deleteCate(@PathVariable(value = "id") Long cateId){
        Category category = categoryRepository.findById(cateId)
                                        .orElseThrow(() -> new CategoryNotFoundException(cateId));
        this.categoryRepository.delete(category);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

}
