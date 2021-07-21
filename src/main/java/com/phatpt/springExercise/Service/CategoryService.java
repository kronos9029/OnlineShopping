package com.phatpt.springExercise.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Exception.CategoryNotFoundException;
import com.phatpt.springExercise.Repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {


    private final CategoryRepository categoryRepository;

    private final ProductService productService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    // Get All Category
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get Cate By ID
    public ResponseEntity<Category> getProductById(Long cateId) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new CategoryNotFoundException(cateId));

        return ResponseEntity.ok().body(category);
    }

    // Save Cate
    public Category createCate(Category newCate) {
        return this.categoryRepository.save(newCate);
    }

    // Update Cate
    public ResponseEntity<Category> updateCategory(Category cateDetail, Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new CategoryNotFoundException(cateId));

        category.setCateName(cateDetail.getCateName());
        category.setCateDescription(cateDetail.getCateDescription());

        return ResponseEntity.ok(this.categoryRepository.save(category));
    }

    // Delete Product
    public Map<String, Boolean> deleteCate(Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new CategoryNotFoundException(cateId));

        List<Product> listProduct = productService.findAllProductsByCateId(category.getCateId());
        if (listProduct.size() != 0) {
            for (Product product : listProduct) {
                productService.setProductCategoryIfDeleted(product);
            }
        }
        this.categoryRepository.delete(category);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

}
