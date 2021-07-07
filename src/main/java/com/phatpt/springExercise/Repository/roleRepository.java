package com.phatpt.springExercise.Repository;

import java.util.Optional;

import com.phatpt.springExercise.Entity.Role;
import com.phatpt.springExercise.Entity.RoleName;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
    Optional<Role> findByName(RoleName roleName);
}
