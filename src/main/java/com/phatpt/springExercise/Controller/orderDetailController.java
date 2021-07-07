package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Service.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @Autowired
    public OrderDetailController(com.phatpt.springExercise.Service.OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/")
    public List<OrderDetail> getAllOrderDetail(){
        return this.orderDetailService.getAllOrderDetail();
    }

    @GetMapping("/orderId?{orderId}")
    public List<OrderDetail> getDetailByOrderId(@RequestParam("orderId") Long orderId){
        return this.orderDetailService.getDetailByOrderId(orderId);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteOrderDetailById(@PathVariable(value = "id") Long detailId){
        return this.orderDetailService.deleteOrderDetailById(detailId);
    }

    @PostMapping("/")
    public OrderDetail createOrderDetail(@RequestBody OrderDetail newDetail){
        return this.orderDetailService.createOrderDetail(newDetail);
    }
}
