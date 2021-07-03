package com.phatpt.springExercise.Repository;

import java.util.Optional;

import com.phatpt.springExercise.Entity.Role;
import com.phatpt.springExercise.Entity.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface roleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleName role);
}
