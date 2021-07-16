package com.phatpt.springExercise.CartTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.phatpt.springExercise.SpringExerciseApplication;
import com.phatpt.springExercise.Controller.ShoppingCartController;
import com.phatpt.springExercise.Entity.ShoppingCart;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringExerciseApplication.class,
 webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartTestController {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port = 5432;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testAddProduct_ThrowNOtFound() {
         Long id = 2L;
       ShoppingCart addProducCart = restTemplate.getForObject(getRootUrl() + "/cart/" + id, 
       ShoppingCart.class);
        
       ResponseEntity<ShoppingCart> postResponse = restTemplate.postForEntity(getRootUrl() 
       + "/public/cart/", addProducCart, ShoppingCart.class);
         try {
           addProducCart = restTemplate.getForObject(getRootUrl() + "/cart/" + id, ShoppingCartController.class);
           assertNotNull(postResponse);
           assertNotNull(postResponse.getBody()); 
       } catch (final HttpClientErrorException e) {
              assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
    }
 
   @Test
   public void testDeleteProduct_ThrowNOtFound() {
        Long id = 2L;
        ShoppingCart removeProductCart = restTemplate.getForObject(getRootUrl() + "/cart/" + id, ShoppingCart.class);
        assertNotNull(removeProductCart);
        restTemplate.delete(getRootUrl() + "/cart/" + id);
        try {
           removeProductCart = restTemplate.getForObject(getRootUrl() + "/cart/" + id, ShoppingCart.class);
        } catch (final HttpClientErrorException e) {
             assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
   }
}
