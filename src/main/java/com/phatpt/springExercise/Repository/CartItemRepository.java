package com.phatpt.springExercise.Repository;

import com.phatpt.springExercise.Entity.shoppingCart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<shoppingCart, Long>{
    
}
