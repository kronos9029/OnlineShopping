package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Service.productService;

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
@RequestMapping("/api/auth")
public class productController {

    private final productService productService;

    @Autowired
    public productController(com.phatpt.springExercise.Service.productService productService) {
        this.productService = productService;
    }

    //Get All Product
    @GetMapping("/products")
    public List<Product> getAllProduct(){
        return (List<Product>) productService.getAllProduct();
    }

    //Get Product By ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) {
        return productService.getProductById(productId);
    }

    //Save Product
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product newProduct){
        return productService.createProduct(newProduct);
    }

    //Update Product
    @PatchMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetail, @PathVariable(value = "id") Long productId){
        return productService.updateProduct(productDetail, productId);
    }
    
    //Delete Product
    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId){
        return productService.deleteProduct(productId);
    }
    
    //Find All Product By CateID
    @GetMapping("/products/byid/{cateId}")
    //request param @GetMapping("/products?cateId=")<---
    public List<Product> findAlProductsByCateId(@PathVariable(value = "cateId") long cateId){
        return productService.findAllProductsByCateId(cateId);
    }
}
