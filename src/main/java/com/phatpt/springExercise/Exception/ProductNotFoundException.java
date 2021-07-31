package com.phatpt.springExercise.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id){
        super("Could Not Find Product "+ id);
    }
}
