package com.phatpt.springExercise.controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.entity.OrderDetail;
import com.phatpt.springExercise.service.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderDetails")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("")
    public List<OrderDetail> getAllOrderDetail(){
        return this.orderDetailService.getAllOrderDetail();
    }

    @GetMapping("/{orderId}")
    public List<OrderDetail> getDetailByOrderId(@PathVariable("orderId") Long orderId){
        return this.orderDetailService.getDetailByOrderId(orderId);
    }

    @DeleteMapping("/{detailsId}")
    public Map<String, Boolean> deleteOrderDetailById(@PathVariable(value = "detailsId") Long detailId) throws Exception{
        return this.orderDetailService.deleteOrderDetailById(detailId);
    }

    @PutMapping("/{detailsId}")
    public OrderDetail feedBack(@PathVariable(value = "detailsId") long detailId, @RequestBody OrderDetail  orderDetail){
        return this.orderDetailService.feedBack(detailId, orderDetail);
    }
}
