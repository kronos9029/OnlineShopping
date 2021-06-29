package com.phatpt.springExercise.Exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id){
        super("Could Not Find Product "+ id);
    }
}
