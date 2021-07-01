package com.phatpt.springExercise.Exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long orderId){
        super("Order with "+orderId+" ID Not Found!!");
    }
}