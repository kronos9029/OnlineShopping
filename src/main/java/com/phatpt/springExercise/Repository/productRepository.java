package com.phatpt.springExercise.Repository;

import com.phatpt.springExercise.Entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends JpaRepository<Product, Long>{
    
}
