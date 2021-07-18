package com.phatpt.springExercise.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(com.phatpt.springExercise.Service.ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public Page<Product> getAllProduct(@RequestParam("page") Optional<Integer> page,  @RequestParam("sortBy")Optional<String> sortBy){
        return (Page<Product>) productService.getAllProduct(page, sortBy);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "productId") Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("")
    public Product createProduct(@RequestBody Product newProduct, @RequestParam("cateId") long cateId) throws Exception{
        return productService.createProduct(newProduct, cateId);
    }

    @PutMapping("")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productDetail, @RequestParam(value = "id") Long productId){
        return productService.updateProduct(productDetail, productId);
    }
    
    @DeleteMapping("/{productId}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "productId") Long productId){
        return productService.deleteProduct(productId);
    }
    
    @GetMapping("/ByCate")
    @ResponseBody
    public List<Product> getAllProductsByCateId(@RequestParam("cateId") String cateId){
        List<Product> productList = new ArrayList<>();
        try {
            long id = Long.parseLong(cateId);
            productList = productService.findAllProductsByCateId(id);
        } catch (Exception e) {
            e.getMessage();
        }
        return productList;
    }

    @GetMapping("/ByName")
    public List<Product> getProductByName(@RequestParam("productName") String productName) throws Exception{
        return productService.findProductByName(productName);
    }
}
