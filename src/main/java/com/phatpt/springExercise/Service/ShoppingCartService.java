package com.phatpt.springExercise.Service;

import javax.servlet.http.HttpServletRequest;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.ProductNotFoundException;
import com.phatpt.springExercise.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ShoppingCart addProductToCart(long productId,HttpServletRequest request) throws Exception{
        Product newProduct = productRepository.findById(productId)
                                              .orElseThrow(() -> new ProductNotFoundException(productId));
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");
        if(shoppingCart == null){
            shoppingCart = new ShoppingCart();
        }
        shoppingCart.addToCart(newProduct);

        request.getSession().setAttribute("shoppingCart", shoppingCart);
        return shoppingCart; 
    }
    

    public ShoppingCart removeProductFromCart(long productId, HttpServletRequest request) throws Exception{ 
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("shoppingCart");

        shoppingCart.remove(productId);

        request.getSession().setAttribute("shoppingCart", shoppingCart);
        return shoppingCart;
    }
    
}
