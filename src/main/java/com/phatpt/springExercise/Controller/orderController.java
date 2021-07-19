package com.phatpt.springExercise.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.phatpt.springExercise.Constant.ErrorCode;
import com.phatpt.springExercise.Constant.SuccessCode;
import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.Response;
import com.phatpt.springExercise.Service.OrderService;
import com.phatpt.springExercise.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
    private final OrderService orderService;

    private final ProductService productService;

    private Response responseObj;

    @Autowired
        public OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getAllOrder(){
        try {
            List<Order> listOrder = orderService.getAllOrder();
            if(listOrder.isEmpty()){
                responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.ORDER_GET_SUCCESS);
                responseObj.setData(listOrder);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            throw new RuntimeException("ERROR AT OrderController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @GetMapping("")
    public ResponseEntity<Response> getOrderByUserId(@RequestParam(value = "userId") Long userId) {
        try {
            List<Order> orderList = orderService.getOrdersByUserId(userId);
            if(orderList.isEmpty()){
                responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.ORDER_GET_SUCCESS);
                responseObj.setData(orderList);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            throw new RuntimeException("ERROR AT OrderController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Response> deleteOrder(@PathVariable(name = "orderId") Long orderId){
        try {
            if(orderService.deleteOrder(orderId) == false){
                responseObj.setErrorCode(ErrorCode.ORDER_DELETE_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.ORDER_DELETE_SUCCESS);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_DELETE_ERROR);
            throw new RuntimeException("ERROR AT OrderController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Response> createOrder(HttpSession session) throws Exception{
        try {
            Order newOrder = orderService.createOrder(session);
            responseObj.setSuccessCode(SuccessCode.ORDER_CREATE_SUCCESS);
            responseObj.setData(newOrder);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_ADD_ERROR);
            throw new RuntimeException("ERROR AT OrderController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Response> updateQuantity(HttpSession session) throws Exception{
        try {
            if(productService.updateQuantity(session) != true)
            responseObj.setSuccessCode(SuccessCode.ORDER_CONFIRM_SUCCESS);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_CONFIRM_ERROR);
            throw new RuntimeException("ERROR AT OrderController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PostMapping("/receive")
    public ResponseEntity<Response> updateStatus(@RequestParam("orderId") long orderId){
        try {
            Order order = orderService.updateStatus(orderId);
            responseObj.setSuccessCode(SuccessCode.ORDER_CONFIRM_SUCCESS);
            responseObj.setData(order);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_CONFIRM_ERROR);
            throw new RuntimeException("ERROR AT OrderController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }
    
}
