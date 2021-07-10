package com.phatpt.springExercise.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.OrderDetailNotFoundException;
import com.phatpt.springExercise.Repository.OrderDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailService {
    
    private final OrderDetailRepository orderDetailRepository;

    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
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

    public String createOrderDetail(Order newOrder, HttpSession session) throws Exception{
        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if(shoppingCart == null){
            throw new Exception("Cart Empty!!");
        } else {
            for (Product cartProduct : shoppingCart.getCart().values()) {
                OrderDetail detail = new OrderDetail(cartProduct.getCartQuantity(), newOrder, cartProduct);
                orderDetailRepository.save(detail);
            }
        }
        return "Done!!";
    }
    
}
