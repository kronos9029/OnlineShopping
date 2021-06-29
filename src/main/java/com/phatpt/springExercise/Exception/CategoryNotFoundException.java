package com.phatpt.springExercise.Exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id){
        super("Could Not Find Category "+ id);
    } 
    
}
