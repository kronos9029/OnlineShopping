package com.phatpt.springExercise.Controller;

import javax.servlet.http.HttpServletRequest;

import com.phatpt.springExercise.Constant.ErrorCode;
import com.phatpt.springExercise.Constant.SuccessCode;
import com.phatpt.springExercise.Entity.Response;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    private Response responseObj;
    
    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("")
    public ResponseEntity<Response> addProductToCart(@RequestParam("productId") long productId, HttpServletRequest request) throws Exception{
        try {
            ShoppingCart shoppingCart = shoppingCartService.addProductToCart(productId, request);
            responseObj.setSuccessCode(SuccessCode.CART_ADD_SUCCESS);
            responseObj.setData(shoppingCart);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CART_ADD_ERROR);
            throw new RuntimeException("ERROR AT CartController: "+ e.getMessage());
        }
        return ResponseEntity.ok().body(responseObj);
    }

    @DeleteMapping("")
    public ResponseEntity<Response> removeProductFromCart(@RequestParam("productId") Long productId, HttpServletRequest request) throws Exception{
        try {
            ShoppingCart shoppingCart = shoppingCartService.removeProductFromCart(productId, request);
            responseObj.setSuccessCode(SuccessCode.CART_REMOVE_SUCCESS);
            responseObj.setData(shoppingCart);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CART_REMOVE_ERROR);
            throw new RuntimeException("ERROR AT CartController: "+ e.getMessage());
        }
        return ResponseEntity.ok().body(responseObj);
    }

    
}
