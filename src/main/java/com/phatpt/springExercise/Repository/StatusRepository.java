package com.phatpt.springExercise.Repository;

import java.util.Optional;

import com.phatpt.springExercise.Entity.OrderStatus;
import com.phatpt.springExercise.Entity.StatusName;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<OrderStatus,  Long>{
    
    Optional<OrderStatus> findByName(StatusName statusName);
}
