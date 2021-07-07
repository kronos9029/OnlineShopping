package com.phatpt.springExercise.Service;

import java.util.List;

import com.phatpt.springExercise.Repository.CartItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    private CartItemRepository cartItemRepository;

    @Autowired
    public ShoppingCartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    // public List<>

    
}
