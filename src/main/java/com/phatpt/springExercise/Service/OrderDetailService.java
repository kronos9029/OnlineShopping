package com.phatpt.springExercise.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Entity.OrderStatus;
import com.phatpt.springExercise.Entity.StatusName;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.OrderDetailNotFoundException;
import com.phatpt.springExercise.Repository.OrderDetailRepository;
import com.phatpt.springExercise.Repository.StatusRepository;

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

    public Map<String, Boolean> deleteOrderDetailById(long detailId){
        OrderDetail orderDetail = orderDetailRepository.findById(detailId)
                                                .orElseThrow(() -> new OrderDetailNotFoundException(detailId));
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
