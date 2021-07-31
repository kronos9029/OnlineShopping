package com.phatpt.springExercise.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id){
        super("Could Not Find Category "+ id);
    } 
    
}
