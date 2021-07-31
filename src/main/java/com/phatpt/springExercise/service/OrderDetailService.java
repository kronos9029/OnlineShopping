package com.phatpt.springExercise.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.repository.OrderDetailRepository;
import com.phatpt.springExercise.repository.StatusRepository;
import com.phatpt.springExercise.entity.Order;
import com.phatpt.springExercise.entity.OrderDetail;
import com.phatpt.springExercise.entity.OrderStatus;
import com.phatpt.springExercise.entity.Product;
import com.phatpt.springExercise.entity.ShoppingCart;
import com.phatpt.springExercise.entity.StatusName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {
    
    private final OrderDetailRepository orderDetailRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository, StatusRepository statusRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.statusRepository = statusRepository;
    }

    public List<OrderDetail> getAllOrderDetail(){
        return (List<OrderDetail>) this.orderDetailRepository.findAll();
    }

    public List<OrderDetail> getDetailByOrderId(long orderId){
        return (List<OrderDetail>) this.orderDetailRepository.findDetailByOrderId(orderId);
    }

    public Map<String, Boolean> deleteOrderDetailById(long detailId) throws Exception{
        OrderDetail orderDetail = orderDetailRepository.findById(detailId)
                                                .orElseThrow(() -> new Exception("Order Detail Not Found!!"));
        this.orderDetailRepository.delete(orderDetail);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public List<OrderDetail> createOrderDetail(Order newOrder, HttpSession session) throws Exception{
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        List<OrderDetail> listDetail = new ArrayList<>();
        if(shoppingCart == null){
            throw new Exception("Cart Empty!!");
        } else {
            for (Product cartProduct : shoppingCart.getCart().values()) {
                OrderDetail detail = new OrderDetail(cartProduct.getCartQuantity(), newOrder, cartProduct);
                orderDetailRepository.save(detail);
                listDetail.add(detail);
            }
        }
        
        return listDetail;
    }

    public OrderDetail feedBack(long orderDetailId, OrderDetail detail){
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow();
        OrderStatus status =  statusRepository.findByName(StatusName.RECEIVED).orElseThrow();
        if(orderDetail.getOrder().getStatus().contains(status)){
            orderDetail.setAmount(orderDetail.getAmount());
            orderDetail.setOrder(orderDetail.getOrder());
            orderDetail.setOrderDetailId(orderDetail.getOrderDetailId());
            orderDetail.setProduct(orderDetail.getProduct());
            orderDetail.setFeedback(detail.getFeedback());
            orderDetail.setStar(detail.getStar());
            return orderDetailRepository.save(orderDetail);
        }

        return orderDetail;
        
    }
    
}
