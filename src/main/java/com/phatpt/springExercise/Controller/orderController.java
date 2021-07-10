package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(com.phatpt.springExercise.Service.OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public List<Order> getAllOrder(){
        return this.orderService.getAllOrder();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId) {
        return this.orderService.getOrderById(orderId);
    }

    @GetMapping("/userId?{id}")
    public List<Order> getOrderByEmail(@RequestParam("id") Long userId){
        return this.getOrderByEmail(userId);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteOrder(@PathVariable(name = "id") Long orderId){
        return this.orderService.deleteOrder(orderId);
    }

    @PostMapping("/confirm")
    public Order createOrder(HttpSession session) throws Exception{
        return this.orderService.createOrder(session);
    }
    
}
