package com.phatpt.springExercise.exception;

public class CategoryExistException extends RuntimeException {
    public CategoryExistException(String name){
        super("Category With This Name Already Exist: "+ name);
    }
}
