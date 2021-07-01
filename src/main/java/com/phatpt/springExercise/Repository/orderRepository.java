package com.phatpt.springExercise.Repository;

import com.phatpt.springExercise.Entity.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository extends CrudRepository<Order, Long> {
    
}
