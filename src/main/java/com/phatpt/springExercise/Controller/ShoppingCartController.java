package com.phatpt.springExercise.Controller;

import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("")
    @ResponseBody
    public ShoppingCart addProductToCart(@RequestParam("productId") long productId,
                                         @RequestParam("customerName") String customerName) throws Exception{

        return this.shoppingCartService.addProductToCart(productId, customerName);
    }

    
}
