package com.phatpt.springExercise.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(com.phatpt.springExercise.Service.ProductService productService) {
        this.productService = productService;
    }

    //Get All Product
    @GetMapping("/")
    public List<Product> getAllProduct(){
        return (List<Product>) productService.getAllProduct();
    }

    //Get Product By ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) {
        return productService.getProductById(productId);
    }

    //Save Product
    @PostMapping("/")
    public Product createProduct(@RequestBody Product newProduct){
        return productService.createProduct(newProduct);
    }

    //Update Product
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetail, @PathVariable(value = "id") Long productId){
        return productService.updateProduct(productDetail, productId);
    }
    
    //Delete Product
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId){
        return productService.deleteProduct(productId);
    }
    
    //Find All Product By CateID
    @GetMapping("")
    @ResponseBody
    public List<Product> findAlProductsByCateId(@RequestParam("cateId") String cateId){
        List<Product> productList = new ArrayList<>();
        try {
            long id = Long.parseLong(cateId);
            productList = productService.findAllProductsByCateId(id);
        } catch (Exception e) {
            e.getMessage();
        }
        return productList;
    }
}
