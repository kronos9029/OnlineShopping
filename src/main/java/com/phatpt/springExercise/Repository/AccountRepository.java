package com.phatpt.springExercise.repository;

import java.util.Optional;

import com.phatpt.springExercise.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    
    Optional<Account> findByUsername(String username);

    @Query("FROM Account WHERE username = ?1")
    Account getAccountByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
