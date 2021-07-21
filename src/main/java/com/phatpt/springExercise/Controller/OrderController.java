package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Service.OrderService;
import com.phatpt.springExercise.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
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

    private final ProductService productService;

    @Autowired
        public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Order> getAllOrder(){
        return this.orderService.getAllOrder();
    }

    @GetMapping("")
    public List<Order> getOrderByUserId(@RequestParam(value = "userId") Long userId) {
        return this.orderService.getOrdersByUserId(userId);
    }

    @DeleteMapping("/{orderId}")
    public Map<String, Boolean> deleteOrder(@PathVariable(name = "orderId") Long orderId){
        return this.orderService.deleteOrder(orderId);
    }

    @PostMapping("/checkout")
    public Order createOrder(HttpSession session) throws Exception{
        return this.orderService.createOrder(session);
    }

    @PostMapping("/confirm")
    public ShoppingCart updateProduct(HttpSession session) throws Exception{
        return this.productService.updateQuantity(session);
    }

    @PostMapping("/receive")
    public Order updateStatus(@RequestParam("orderId") long id){
        return this.orderService.updateStatus(id);
    }
    
}
