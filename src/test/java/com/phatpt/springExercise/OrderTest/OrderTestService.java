package com.phatpt.springExercise.OrderTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.phatpt.springExercise.Repository.OrderRepository;
import com.phatpt.springExercise.Service.OrderService;
import com.phatpt.springExercise.Entity.Order;
import com.phatpt.springExercise.Entity.StatusName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class OrderTestService {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    Order Order;
    StatusName statusName;
    public static final String PRODUCTNAME = "PRODUCTNAME";
    public static final String STATUSNAME = "RECEIVED";
    public static final Long ID = 1L;
    List<Order> list;

    @BeforeEach
    public void setUpOrder() {
        list = new ArrayList<>();
        Order order = new Order(123, "customerName", "0941299811", "sky9", new Date());
        list.add(order);
    }

    @Test
    public void getAllTest_returnOrderList() throws Exception {
        when(orderRepository.findAll()).thenReturn(list);
        assertEquals(orderService.getAllOrder(), list);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void whenValidID_thenOrderShouldBeFound() throws Exception {

        Order Order = new Order();
        Optional<Order> optional = Optional.of(Order);
        assertNotNull(optional);
        when(orderRepository.findById(ID)).thenReturn(optional);
        ResponseEntity<Order> Order2 = orderService.getOrderById(ID);
        assertEquals(Order2.getBody().getCustomerName(), Order.getCustomerName());
    }
    @Test
    public void whenValidID_thenDeleteOrderShouldBeFound() throws Exception {
        Order Order = new Order();
        Optional<Order> optional = Optional.of(Order);
        assertNotNull(optional);
        when(orderRepository.findById(ID)).thenReturn(optional);
        Map<String, Boolean> Order2 = orderService.deleteOrder(ID);
        assertEquals(Order2.equals(true), false);
    }
    @Test
    public void updateStatus_ThenReturnOrder() throws Exception {
        Order Order = new Order();
        Optional<Order> optional = Optional.of(Order);
        assertNotNull(optional);
        when(orderRepository.findById(ID)).thenReturn(optional);   
        orderService.updateStatus(ID);
    }
    // @Test
    // public void createOrder_ThenReturnOrder() throws Exception {
    //     when(orderRepository.save(list.get(0))).thenReturn(list.get(0));
    //     assertEquals(orderService.createOrder(list.get(0)), list.get(0));
    // }
}
