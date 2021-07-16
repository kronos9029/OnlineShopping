package com.phatpt.springExercise.OrderTest;
import static org.junit.Assert.assertNotNull;

import java.util.Date;


import com.phatpt.springExercise.SpringExerciseApplication;
import com.phatpt.springExercise.Entity.Order;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringExerciseApplication.class,
 webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderTestController {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port = 5432;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testGetAllOrder_ReturnBody() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/api/orders/",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }
   @Test
   public void testGetOrderById_ThrowNotNull() {
       Order order = restTemplate.getForObject(getRootUrl() 
       + "/api/orders/1", Order.class);
       assertNotNull(order);
   }
   @Test
   public void testCreateorderDetail() {
       Order order = new Order();
       order.setOrderId(1L);
       order.setCustomerAddress("q12");
       order.setCustomerName("Dat");
       order.setCustomerPhone("0123456789");
       order.setTotalMoney(123);
       order.setCreateDate(new Date());
     
       ResponseEntity<Order> postResponse = restTemplate.postForEntity(getRootUrl() 
       + "/public/orders/", order, Order.class);
       assertNotNull(postResponse);
       assertNotNull(postResponse.getBody());
   }
}
