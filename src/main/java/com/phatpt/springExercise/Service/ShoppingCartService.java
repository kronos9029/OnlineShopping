package com.phatpt.springExercise.Service;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.ProductNotFoundException;
import com.phatpt.springExercise.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    
    private final ProductRepository productRepository;
    private ShoppingCart shoppingCart;

    @Autowired
    public ShoppingCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ShoppingCart addProductToCart(long productId, String customerName) throws Exception{
        Product newProduct = productRepository.findById(productId)
                                              .orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart == null){
            shoppingCart = new ShoppingCart(customerName);
        }
        shoppingCart.addToCart(newProduct);
        return shoppingCart; 
    }
    

    public ShoppingCart removeProductFromCart(long productId) throws Exception{
        
        shoppingCart.remove(productId);

        return shoppingCart;
    }
    
}
