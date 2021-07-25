package com.phatpt.springExercise.Validate;

import com.phatpt.springExercise.payload.request.LoginRequest;

public class Validate {
    
    public static void validateLogin(LoginRequest loginRequest){
        try {
            if(loginRequest.getUsername().length() == 0 || loginRequest.getPassword().length() == 0){
                throw new Exception();
            } else if(loginRequest.getUsername().length() == 0 && loginRequest.getPassword().length() == 0){
                throw new Exception();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
