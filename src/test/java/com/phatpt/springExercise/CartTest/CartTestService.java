package com.phatpt.springExercise.CartTest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.phatpt.springExercise.entity.Category;
import com.phatpt.springExercise.entity.Product;
import com.phatpt.springExercise.entity.ShoppingCart;
import com.phatpt.springExercise.repository.ProductRepository;
import com.phatpt.springExercise.service.ShoppingCartService;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CartTestService {
    @Autowired
    private ShoppingCartService cartService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    ShoppingCart cart;
    
    public static final Long PRODUCTID = 1L;
    public HttpServletRequest request;

    List<Product> list;


    @BeforeEach
    public void setUp(){
        list = new ArrayList<>();
        Category category = new Category("Laptop", "strong laptop");
        Product product = new Product("MSI RF", 17000000, "abcd.img", new Date(), new Date(), "productDescription", category, 100, "ACTIVE");
        Product product2 = new Product("MSI RG", 17000000, "abcd.img", new Date(), new Date(), "productDescription", category, 100, "ACTIVE");
        Product product3 = new Product("LENOVO LEGION", 17000000, "abcd.img", new Date(), new Date(), "productDescription", category, 100, "ACTIVE");
        list.add(product);
        list.add(product2);
        list.add(product3);
    }
    @Test
    public void addProductToCart_ThenReturnCart() throws Exception {
        Optional<Product> optional = Optional.of(list.get(0));
        assertNotNull(optional);
        when(productRepository.findById(PRODUCTID)).thenReturn(optional);
        ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("shCart");
        shoppingCart = new ShoppingCart();
        shoppingCart.addToCart(list.get(0));
        request.getSession().setAttribute("shCart", shoppingCart);
        cartService.addProductToCart(PRODUCTID, request);
    }


    @Test
    public void removeProductCart_thenDeleteProdutcfromCartShouldBeFound(HttpServletRequest request) throws Exception {

        Product product = new Product();
        Optional<Product> optional = Optional.of(product);
 
         // (1L,"ASUS", "CoreI7", new Date(), new Date(),15000000,"Avaliable","abc.img", 11);
         // list.add(product);
         assertNotNull(optional);
         when(productRepository.findById(PRODUCTID)).thenReturn(optional);
         equals(cartService.removeProductFromCart(PRODUCTID, request));

    }

}
