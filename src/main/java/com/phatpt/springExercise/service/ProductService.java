package com.phatpt.springExercise.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.repository.CategoryRepository;
import com.phatpt.springExercise.repository.ProductRepository;
import com.phatpt.springExercise.entity.Category;
import com.phatpt.springExercise.entity.Product;
import com.phatpt.springExercise.entity.ShoppingCart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    Date currentDate = new Date();

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Get All Product
    public List<Product> getAllProduct() {
        return (List<Product>) this.productRepository.findAll();
    }

    public List<Product> getAllActiveProduct() {
        return this.productRepository.findActiveProduct();
    }

    // Get Product By ID
    public ResponseEntity<Product> getProductById(Long productId) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product Not Found!!"));


        return ResponseEntity.ok().body(product);
    }

    public Product createProduct(Product newProduct, long cateId) throws Exception {
        Category category = categoryRepository.findCateById(cateId);
        if (category == null) {
            throw new Exception("Category Not Found!!");
        }
        newProduct.setCreateDate(currentDate);
        newProduct.setCategory(category);
        newProduct.setCartQuantity(0);
        newProduct.setStatus("ACTIVE");
        return this.productRepository.save(newProduct);

    }

    // Update Product
    public ResponseEntity<Product> updateProduct(Product productDetail, Long productId) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product Not Found!!"));

        product.setProductName(productDetail.getProductName());
        product.setCategory(productDetail.getCategory());
        product.setProductPrice(productDetail.getProductPrice());
        product.setImage(productDetail.getImage());
        product.setProductDescription(productDetail.getProductDescription());
        product.setUpdateDate(currentDate);
        product.setStatus(productDetail.getStatus());
        product.setQuantity(productDetail.getQuantity());
        product.setCartQuantity(0);

        return ResponseEntity.ok(this.productRepository.save(product));
    }

    // Delete Product
    public Map<String, Boolean> deleteProduct(Long productId) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product Not Found!!"));

        if (product == null) {
            throw new Exception("ERROR AT Delete Controller");
        }
        this.productRepository.deleteProduct(productId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public List<Product> findAllProductsByCateId(long cateId) {
        return this.productRepository.findAllProductsByCateId(cateId);
    }

    public Map<String, Boolean> setProductCategoryIfDeleted(Product product) {
        Category none = categoryRepository.findCateById(3L);
        product.setCategory(none);
        this.productRepository.save(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Set NULL", Boolean.TRUE);
        return response;
    }

    public ShoppingCart updateQuantity(HttpSession session) throws Exception {
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) {
            throw new Exception("Cart Empty!!");
        }

        for (Product cartProduct : shoppingCart.getCart().values()) {
            Product product = productRepository.findProductById(cartProduct.getProductId());
            int storage = product.getQuantity();
            int buyQuantity = cartProduct.getCartQuantity();
            int remain = storage - buyQuantity;
            this.productRepository.updateQuantity(remain, cartProduct.getProductId());
        }
        session.removeAttribute("shoppingCart");
        return shoppingCart;
    }

    public List<Product> findProductByName(String name) throws Exception {
        List<Product> emptyList = new ArrayList<>();

        if(name.isEmpty()){
            return emptyList;
        }
        List<Product> productList = productRepository.findByproductNameContainingIgnoreCase(name);
        return productList;
    }

    public List<Product> findproductByNameCustomer(String name) throws Exception {
        List<Product> productList = productRepository.findByproductNameContainingIgnoreCase(name);
        List<Product> showList = new ArrayList<>();

        if(name.isEmpty()){
            return showList;
        }

        for (Product product : productList) {
            if (product.getStatus().equals("ACTIVE")) {
                showList.add(product);
            }
        }

        if (showList.isEmpty()) {
            return showList;
        }

        return showList;
    }
}
