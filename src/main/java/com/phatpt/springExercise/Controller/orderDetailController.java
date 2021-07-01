package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Service.orderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class orderDetailController {

    private final orderDetailService orderDetailService;

    @Autowired
    public orderDetailController(com.phatpt.springExercise.Service.orderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/orders/orderDetails")
    public List<OrderDetail> getAllOrderDetail(){
        return this.orderDetailService.getAllOrderDetail();
    }

    @GetMapping("/orders/orderDetails/{id}")
    public List<OrderDetail> getDetailByOrderId(@PathVariable(value = "id") Long orderId){
        return this.orderDetailService.getDetailByOrderId(orderId);
    }

    @DeleteMapping("/orders/orderDetails/{id}")
    public Map<String, Boolean> deleteOrderDetailById(@PathVariable(value = "id") Long detailId){
        return this.orderDetailService.deleteOrderDetailById(detailId);
    }

    @PostMapping("/orders/orderDetails")
    public OrderDetail createOrderDetail(@RequestBody OrderDetail newDetail){
        return this.orderDetailService.createOrderDetail(newDetail);
    }
}
