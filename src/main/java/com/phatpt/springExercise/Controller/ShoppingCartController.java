package com.phatpt.springExercise.Controller;

import javax.servlet.http.HttpServletRequest;

import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

<<<<<<< HEAD
    private Response responseObj = new Response();
    
=======
>>>>>>> parent of eadb109... Refactor controller code
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("")
    public ShoppingCart addProductToCart(@RequestParam("productId") long productId, HttpServletRequest request) throws Exception{
        return this.shoppingCartService.addProductToCart(productId, request);
    }

    @DeleteMapping("")
    public ShoppingCart removeProductFromCart(@RequestParam("productId") Long productId, HttpServletRequest request) throws Exception{
        return this.shoppingCartService.removeProductFromCart(productId, request);
    }

    
}
