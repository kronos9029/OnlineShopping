package com.phatpt.springExercise.repository;

import java.util.List;

import com.phatpt.springExercise.entity.OrderDetail;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long>{
    
    @Query("FROM OrderDetail WHERE order_id = ?1")
    List<OrderDetail> findDetailByOrderId(long orderId);

    
}
