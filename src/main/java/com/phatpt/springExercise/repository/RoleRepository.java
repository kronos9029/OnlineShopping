package com.phatpt.springExercise.repository;

import java.util.Optional;

import com.phatpt.springExercise.entity.Role;
import com.phatpt.springExercise.entity.RoleName;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
    Optional<Role> findByName(RoleName roleName);
}
