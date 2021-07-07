package com.phatpt.springExercise.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Exception.OrderDetailNotFoundException;
import com.phatpt.springExercise.Repository.OrderDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {
    
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(com.phatpt.springExercise.Repository.OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetail> getAllOrderDetail(){
        return (List<OrderDetail>) this.orderDetailRepository.findAll();
    }

    public List<OrderDetail> getDetailByOrderId(long orderId){
        return (List<OrderDetail>) this.orderDetailRepository.findDetailByOrderId(orderId);
    }

    public Map<String, Boolean> deleteOrderDetailById(long detailId){
        OrderDetail orderDetail = orderDetailRepository.findById(detailId)
                                                .orElseThrow(() -> new OrderDetailNotFoundException(detailId));
        this.orderDetailRepository.delete(orderDetail);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public OrderDetail createOrderDetail(OrderDetail newDetail){
        return this.orderDetailRepository.save(newDetail);
    }
    
}
