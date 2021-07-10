package com.phatpt.springExercise.Repository;

import java.util.List;

import com.phatpt.springExercise.Entity.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("FROM Order WHERE user_id = ?1")
    List<Order> findOrdersByUserId(Long userId);

    // @Query("INSERT INTO Order VALUES()")
    // boolean createOrder(float totalMoney, 
    //                     String customerName, 
    //                     String customerPhone, 
    //                     String customerAddress, 
    //                     Date createDate, 
    //                     Account account);

}
