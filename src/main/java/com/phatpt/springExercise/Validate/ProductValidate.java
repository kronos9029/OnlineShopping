package com.phatpt.springExercise.Validate;

import com.phatpt.springExercise.Entity.Product;

public class ProductValidate {
    
    public static void productValidate(Product newProduct){
        try {
            if(newProduct.getProductName().length() == 0){
                throw new Exception();
            }
        } catch (Exception e) {
            //TODO: handle exception
        }

    }


}
