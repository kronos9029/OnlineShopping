package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Service.orderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class orderController {
    private final orderService orderService;

    @Autowired
    public orderController(com.phatpt.springExercise.Service.orderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrder(){
        return this.orderService.getAllOrder();
    }


    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId) {
        return this.orderService.getOrderById(orderId);
    }

    @PostMapping("/orders")
    public Order createOrder(@RequestBody Order newOrder){
        return this.orderService.createOrder(newOrder);
    }

    @DeleteMapping("/orders/{id}")
    public Map<String, Boolean> deleteOrder(@PathVariable(name = "id") Long orderId){
        return this.orderService.deleteOrder(orderId);
    }

    
}
