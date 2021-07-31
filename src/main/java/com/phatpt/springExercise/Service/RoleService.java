package com.phatpt.springExercise.service;

import java.util.List;

import com.phatpt.springExercise.exception.RoleExistException;
import com.phatpt.springExercise.repository.RoleRepository;
import com.phatpt.springExercise.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(com.phatpt.springExercise.repository.RoleRepository roleRepository) {
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
