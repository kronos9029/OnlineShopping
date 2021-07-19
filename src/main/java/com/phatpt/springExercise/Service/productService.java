package com.phatpt.springExercise.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.ProductNotFoundException;
import com.phatpt.springExercise.Repository.CategoryRepository;
import com.phatpt.springExercise.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
        return this.productRepository.findAll();
    }

    // Get Product By ID
    public Product getProductById(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return product;
    }

    public Product createProduct(Product newProduct, long cateId) throws Exception {
        Category category = categoryRepository.findCateById(cateId);
        if (category == null) {
            throw new Exception("Category Not Found!!");
        }
        newProduct.setCreateDate(currentDate);
        newProduct.setCategory(category);
        newProduct.setCartQuantity(0);
        return this.productRepository.save(newProduct);

    }

    // Update Product
    public Product updateProduct(Product productDetail, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        product.setProductName(productDetail.getProductName());
        product.setCategory(productDetail.getCategory());
        product.setProductPrice(productDetail.getProductPrice());
        product.setImage(productDetail.getImage());
        product.setProductDescription(productDetail.getProductDescription());
        product.setUpdateDate(currentDate);
        product.setCartQuantity(0);

        return product;
    }

    // Delete Product
    public Boolean deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        this.productRepository.delete(product);

        return Boolean.TRUE;
    }

    public List<Product> findAllProductsByCateId(long cateId) {
        return this.productRepository.findAllProductsByCateId(cateId);
    }

    public Map<String, Boolean> setProductCategoryIfDeleted(Product product) {
        product.setCategory(null);
        this.productRepository.save(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Set NULL", Boolean.TRUE);
        return response;
    }

    public boolean updateQuantity(HttpSession session) throws Exception {
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

        return Boolean.TRUE;
    }

    public List<Product> findProductByName(String name) throws Exception{
        List<Product> productList =  productRepository.findByproductNameContainingIgnoreCase(name);
        if(productList.isEmpty()){
            throw new Exception("Product Not Found!!");
        }
        return productList;
    }
}
