package com.phatpt.springExercise.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.exception.OrderNotFoundException;
import com.phatpt.springExercise.repository.OrderRepository;
import com.phatpt.springExercise.repository.ProductRepository;
import com.phatpt.springExercise.repository.StatusRepository;
import com.phatpt.springExercise.entity.Account;
import com.phatpt.springExercise.entity.Order;
import com.phatpt.springExercise.entity.OrderDetail;
import com.phatpt.springExercise.entity.OrderStatus;
import com.phatpt.springExercise.entity.Product;
import com.phatpt.springExercise.entity.ShoppingCart;
import com.phatpt.springExercise.entity.StatusName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final ProductRepository productRepository;
    private final AccountService accountService;
    private final StatusRepository statusRepository;
    Date currentDate = new Date();

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService,
            ProductRepository productRepository, AccountService accountService, StatusRepository statusRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.productRepository = productRepository;
        this.accountService = accountService;
        this.statusRepository = statusRepository;
    }

    public List<Order> getAllOrder() {
        return (List<Order>) this.orderRepository.findAll();
    }

    public ResponseEntity<Order> getOrderById(long orderId) throws OrderNotFoundException {
        Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

        return ResponseEntity.ok().body(order);
    }

    public Order createOrder(HttpSession session) throws Exception {

        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
        if (shoppingCart == null) {
            throw new Exception("Cart Empty!!");
        }

        for (Product cartProduct : shoppingCart.getCart().values()) {
            Optional<Product> vaultProduct = productRepository.findById(cartProduct.getProductId());
            if (cartProduct.getCartQuantity() > vaultProduct.get().getQuantity()) {
                throw new Exception("Sorry We only have " + vaultProduct.get().getQuantity() + " Left!!");
            }
        }

        String username = (String) session.getAttribute("currentUsername");
        Account currentUser = accountService.getAccountByUsername(username);
        
        Order newOrder = new Order(shoppingCart.getTotal(), currentUser.getFullName(), currentUser.getPhone(),
                currentUser.getAddress(), new Date(), currentUser);

        Set<OrderStatus> status = new HashSet<>();
        OrderStatus statusName = statusRepository.findByName(StatusName.ORDERING).orElseThrow();
        status.add(statusName);
        newOrder.setStatus(status);
        orderRepository.save(newOrder);
        orderDetailService.createOrderDetail(newOrder, session);

        return newOrder;
    }

    public Map<String, Boolean> deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        List<OrderDetail> detailList = this.orderDetailService.getDetailByOrderId(order.getOrderId());
        if (detailList.size() != 0) {
            for (OrderDetail orderDetail : detailList) {
                this.orderDetailService.deleteOrderDetailById(orderDetail.getOrderDetailId());
            }
        }
        this.orderRepository.delete(order);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);

        return response;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findOrdersByUserId(userId);
    }

    public Order updateStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        Set<OrderStatus> status = new HashSet<>();
        OrderStatus statusName = statusRepository.findByName(StatusName.RECEIVED).orElseThrow();
        status.add(statusName);
        order.setStatus(status);
        return orderRepository.save(order);
    }

}
