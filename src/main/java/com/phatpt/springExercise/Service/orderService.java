package com.phatpt.springExercise.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Entity.Account;
import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.ShoppingCart;
import com.phatpt.springExercise.Exception.OrderNotFoundException;
import com.phatpt.springExercise.Repository.OrderRepository;
import com.phatpt.springExercise.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final ProductRepository productRepository;
    private final AccountService accountService;
    private final ProductService productService;
    Date currentDate = new Date();

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService,
            ProductRepository productRepository, AccountService accountService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.productRepository = productRepository;
        this.accountService = accountService;
        this.productService = productService;
    }

    public List<Order> getAllOrder(){
        return (List<Order>) this.orderRepository.findAll();
    }

    public ResponseEntity<Order> getOrderById(long orderId) throws OrderNotFoundException{
        Order order = this.orderRepository.findById(orderId)
                                            .orElseThrow(() -> new OrderNotFoundException(orderId));

        return ResponseEntity.ok().body(order);
    }

    public Order createOrder(HttpSession session) throws Exception{

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");

        for (Product cartProduct : shoppingCart.getCart().values()) {
            Optional<Product> vaultProduct = productRepository.findById(cartProduct.getProductId());
            if(cartProduct.getCartQuantity() > vaultProduct.get().getQuantity()){
                throw new Exception("Sorry We only have "+vaultProduct.get().getQuantity()+" Left!!");
            }
        }

        String username = (String) session.getAttribute("currentUsername");

        Account currentUser = accountService.getAccountByUsername(username);
        Order newOrder = new Order(shoppingCart.getTotal(), 
                                   currentUser.getFullName(), 
                                   currentUser.getPhone(), 
                                   currentUser.getAddress(), 
                                   new Date(),
                                   currentUser);
        orderRepository.save(newOrder);
        orderDetailService.createOrderDetail(newOrder, session);
        for (Product cartProduct : shoppingCart.getCart().values()) {
            int remain = cartProduct.getQuantity() - cartProduct.getCartQuantity();
            productService.updateQuantity(remain, cartProduct);
        }
        
        return newOrder;
    }

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
