package com.phatpt.springExercise.Exception;

public class OrderDetailNotFoundException extends RuntimeException{
    public OrderDetailNotFoundException(Long id){
        super("Order Detail Not Found!!");
    }
}
