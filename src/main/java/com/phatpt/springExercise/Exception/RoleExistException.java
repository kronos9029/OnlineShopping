package com.phatpt.springExercise.Exception;

import com.phatpt.springExercise.Entity.RoleName;

public class RoleExistException extends RuntimeException {
    public RoleExistException(RoleName roleName){
        super("Role With This Name Already Exist: "+ roleName);
    }
    
}
