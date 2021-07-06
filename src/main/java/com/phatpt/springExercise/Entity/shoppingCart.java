package com.phatpt.springExercise.Entity;

import java.util.HashMap;

public class shoppingCart {
    
    private String customerName;
    private HashMap<String, Product> cart;

    public shoppingCart(String customerName, HashMap<String, Product> cart) {
        this.customerName = customerName;
        this.cart = cart;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public HashMap<String, Product> getCart() {
        return cart;
    }

    public void setCart(HashMap<String, Product> cart) {
        this.cart = cart;
    }

    
    
}
