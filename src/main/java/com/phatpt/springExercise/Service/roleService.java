package com.phatpt.springExercise.Service;

import java.util.List;

import com.phatpt.springExercise.Entity.Role;
import com.phatpt.springExercise.Exception.RoleExistException;
import com.phatpt.springExercise.Repository.roleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class roleService {
    private final roleRepository roleRepository;

    @Autowired
    public roleService(com.phatpt.springExercise.Repository.roleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles(){
        return (List<Role>) this.roleRepository.findAll();
    }

    public Role createRole(Role newRole){
        roleRepository.findByName(newRole.getRoleName()).orElseThrow(() -> new RoleExistException(newRole.getRoleName()));
        return this.roleRepository.save(newRole);
    }
    
}
