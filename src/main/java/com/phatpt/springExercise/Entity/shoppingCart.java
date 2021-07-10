package com.phatpt.springExercise.Entity;

import java.util.HashMap;

public class ShoppingCart {

    private HashMap<Long, Product> cart;

    public ShoppingCart() {
        this.cart = new HashMap<>();
    }

    public HashMap<Long, Product> getCart() {
        return cart;
    }

    public void setCart(HashMap<Long, Product> cart) {
        this.cart = cart;
    }

    public void addToCart(Product product) throws Exception{
		if(this.cart.containsKey(product.getProductId())){
			int newQuant = this.cart.get(product.getProductId()).getCartQuantity();
            newQuant++;
            this.cart.get(product.getProductId()).setCartQuantity(newQuant);
		} else{
            product.setCartQuantity(1);
			this.cart.put(product.getProductId(), product);
		}
	}

    public void remove(Long id) throws Exception {
		if(this.cart.containsKey(id)){
			this.cart.remove(id);
		}
	}

    public float getTotal() throws Exception{
		float result = 0;
		for(Product dto : this.cart.values()){
			result += dto.getCartQuantity() * dto.getProductPrice();
		}
		return result;
    }
}
