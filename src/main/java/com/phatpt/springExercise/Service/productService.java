package com.phatpt.springExercise.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.ProductNotFoundException;
import com.phatpt.springExercise.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    private final ProductRepository productRepository;
    Date currentDate = new Date();

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Get All Product
    public List<Product> getAllProduct(){
        return (List<Product>) this.productRepository.findAll();
    }

    //Get Product By ID
    public ResponseEntity<Product> getProductById(Long productId) 
            throws ProductNotFoundException{
        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));
        
        return ResponseEntity.ok().body(product);
    }

    //Save Product
    public Product createProduct(Product newProduct){
        newProduct.setCreateDate(currentDate);
        return this.productRepository.save(newProduct);
    }

    //Update Product
    public ResponseEntity<Product> updateProduct(Product productDetail, Long productId){
        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));
                
        product.setProductName(productDetail.getProductName());
        product.setCategory(productDetail.getCategory());
        product.setProductPrice(productDetail.getProductPrice());
        product.setImage(productDetail.getImage());
        product.setProductDescription(productDetail.getProductDescription());
        product.setUpdateDate(currentDate);

        return ResponseEntity.ok(this.productRepository.save(product));
    }
    
    //Delete Product
    public Map<String, Boolean> deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));
        this.productRepository.delete(product);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public List<Product> findAllProductsByCateId(long cateId){
        return this.productRepository.findAllProductsByCateId(cateId);
    }

    public Map<String, Boolean> setProductCategoryIfDeleted(Product product){
        product.setCategory(null);
        this.productRepository.save(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Set NULL", Boolean.TRUE);
        return response;
    }

    public void updateQuantity(HttpSession session) throws Exception{
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if(shoppingCart == null){
            throw new Exception("Cart Empty!!");
        }

        for (Product cartProduct : shoppingCart.getCart().values()) {
            Product product = productRepository.findProductById(cartProduct.getProductId());
            int storage = product.getQuantity();
            int buyQuantity = cartProduct.getCartQuantity();
            int remain = storage - buyQuantity;
            this.productRepository.updateQuantity(remain, cartProduct.getProductId());
        }
    }
}
