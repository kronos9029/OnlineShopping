package com.phatpt.springExercise.repository;

import java.util.Optional;

import com.phatpt.springExercise.entity.OrderStatus;
import com.phatpt.springExercise.entity.StatusName;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<OrderStatus,  Long>{
    
    Optional<OrderStatus> findByName(StatusName statusName);
}
