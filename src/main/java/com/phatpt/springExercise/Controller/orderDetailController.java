package com.phatpt.springExercise.Controller;

import java.util.List;

import com.phatpt.springExercise.Constant.ErrorCode;
import com.phatpt.springExercise.Constant.SuccessCode;
import com.phatpt.springExercise.Entity.OrderDetail;
import com.phatpt.springExercise.Entity.Response;
import com.phatpt.springExercise.Service.OrderDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private Response responseObj;
    
    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("")
    public ResponseEntity<Response> getAllOrderDetail(){
        try {
            List<OrderDetail> detailList = orderDetailService.getAllOrderDetail();
            if(detailList.isEmpty()){
                responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.ORDER_GET_SUCCESS);
                responseObj.setData(detailList);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            throw new RuntimeException("ERROR AT OrderDetailController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Response> getDetailByOrderId(@PathVariable("orderId") Long orderId){
        try {
            List<OrderDetail> detailList = orderDetailService.getDetailByOrderId(orderId);
            if(detailList.isEmpty()){
                responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.ORDER_GET_SUCCESS);
                responseObj.setData(detailList);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            throw new RuntimeException("ERROR AT OrderDetailController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @DeleteMapping("/{detailsId}")
    public ResponseEntity<Response> deleteOrderDetailById(@PathVariable(value = "detailsId") Long detailId){
        try {
            if(orderDetailService.deleteOrderDetailById(detailId) != true){
                responseObj.setErrorCode(ErrorCode.ORDER_DELETE_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.ORDER_DELETE_SUCCESS);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_GET_ERROR);
            throw new RuntimeException("ERROR AT OrderDetailController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PutMapping("/{detailsId}")
    public ResponseEntity<Response> feedBack(@PathVariable(value = "detailsId") long detailId, @RequestBody OrderDetail  orderDetail){
        try {
            OrderDetail detail  = orderDetailService.feedBack(detailId, orderDetail);
            responseObj.setSuccessCode(SuccessCode.ORDER_FEEDBACK_SUCCESS);
            responseObj.setData(detail);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.ORDER_CONFIRM_ERROR);
            throw new RuntimeException("ERROR AT OrderDetailController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }
}
