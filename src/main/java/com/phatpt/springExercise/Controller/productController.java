package com.phatpt.springExercise.Controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Exception.ProductNotFoundException;
import com.phatpt.springExercise.Repository.productRepository;

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
public class productController {
    
    @Autowired
    private productRepository productRepository;
    
    //Get All Product
    @GetMapping("/products")
    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }

    //Get Product By ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) 
            throws ProductNotFoundException{
        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));
        
        return ResponseEntity.ok().body(product);
    }

    //Save Product
    @PostMapping("/products")
    public Product createProduct(@RequestBody Product newProduct){
        return this.productRepository.save(newProduct);
    }

    //Update Product
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetail, @PathVariable(value = "id") Long productId){
        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));
        
        product.setProductName(productDetail.getProductName());
        product.setCategoryID(productDetail.getCategoryID());
        product.setProductPrice(productDetail.getProductPrice());
        product.setImage(productDetail.getImage());
        product.setProductDescription(productDetail.getProductDescription());
        product.setUpdateDate(new Timestamp(product.getUpdateDate().getTime()));

        return ResponseEntity.ok(this.productRepository.save(product));
    }
    
    //Delete Product
    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId){
        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));
        this.productRepository.delete(product);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }
}
