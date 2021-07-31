package com.phatpt.springExercise.exception;

public class OrderDetailNotFoundException extends RuntimeException{
    public OrderDetailNotFoundException(Long id){
        super("Order Detail Not Found!!");
    }
}
