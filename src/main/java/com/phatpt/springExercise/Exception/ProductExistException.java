package com.phatpt.springExercise.Exception;

public class ProductExistException extends RuntimeException {
    public ProductExistException(String name){
        super("Product With This Name Already Exist: "+ name);
    }

}
