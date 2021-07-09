package com.phatpt.springExercise.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Exception.OrderNotFoundException;
import com.phatpt.springExercise.Repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    Date currentDate = new Date();

    @Autowired
    public OrderService(OrderRepository orderRepository, 
                        OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        }

    public List<Order> getAllOrder(){
        return (List<Order>) this.orderRepository.findAll();
    }

    public ResponseEntity<Order> getOrderById(long orderId) throws OrderNotFoundException{
        Order order = this.orderRepository.findById(orderId)
                                            .orElseThrow(() -> new OrderNotFoundException(orderId));

        return ResponseEntity.ok().body(order);
    }

    // public Order createOrder() throws Exception{
    //     if(shoppingCart == null){
    //         throw new Exception("Shopping Cart Empty!!");
    //     }



    // }

    public Map<String, Boolean> deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                                        .orElseThrow(() -> new OrderNotFoundException(orderId));
        List<OrderDetail> detailList = this.orderDetailService.getDetailByOrderId(order.getOrderId());
        if(detailList.size() != 0){
            for (OrderDetail orderDetail : detailList) {
                this.orderDetailService.deleteOrderDetailById(orderDetail.getOrderDetailId());
            }
        }
        this.orderRepository.delete(order);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public List<Order> getOrdersByUserId(Long userId){
        return orderRepository.findOrdersByUserId(userId);
    }
    
}
