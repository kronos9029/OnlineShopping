package com.phatpt.springExercise.exception;

import com.phatpt.springExercise.entity.RoleName;

public class RoleExistException extends RuntimeException {
    public RoleExistException(RoleName roleName){
        super("Role With This Name Already Exist: "+ roleName);
    }
    
}
