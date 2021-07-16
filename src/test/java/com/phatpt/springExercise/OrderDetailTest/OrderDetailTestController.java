package com.phatpt.springExercise.OrderDetailTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.phatpt.springExercise.SpringExerciseApplication;
import com.phatpt.springExercise.Entity.OrderDetail;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringExerciseApplication.class,
 webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailTestController {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port = 5432;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testGetAllOrderDetail_ReturnBody() {
    HttpHeaders headers = new HttpHeaders();
       HttpEntity<String> entity = new HttpEntity<String>(null, headers);
       ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/public/orderdetails/",
       HttpMethod.GET, entity, String.class);  
       assertNotNull(response.getBody());
   }
   @Test
   public void testGetOrderDetailById_ThrowNotNull() {
       OrderDetail orderDetail = restTemplate.getForObject(getRootUrl() 
       + "/public/orders/orderdetails/1", OrderDetail.class);
       assertNotNull(orderDetail);
   }
   @Test
   public void testCreateorderDetail() {
       OrderDetail orderDetail = new OrderDetail();
       orderDetail.setOrderDetailId(1L);
       orderDetail.setFeedback("nice");
       orderDetail.setStar(3);
     
       ResponseEntity<OrderDetail> postResponse = restTemplate.postForEntity(getRootUrl() 
       + "/public/orders/orderdetails/", orderDetail, OrderDetail.class);
       assertNotNull(postResponse);
       assertNotNull(postResponse.getBody());
   }
 
   @Test
   public void testDeleteOrderDetails_ThrowNOtFound() {
        Long id = 2L;
        OrderDetail orderDetail = restTemplate.getForObject(getRootUrl() + "/public/orders/orderdetails/" + id, OrderDetail.class);
        assertNotNull(orderDetail);
        restTemplate.delete(getRootUrl() + "/public/orders/orderdetails/" + id);
        try {
           orderDetail = restTemplate.getForObject(getRootUrl() + "/public/orders/orderdetails/" + id, OrderDetail.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }
}
