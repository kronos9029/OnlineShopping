package com.phatpt.springExercise.ProductTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Repository.ProductRepository;
import com.phatpt.springExercise.Service.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class ProductTestService {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    Product product;
    public static final String PRODUCTNAME = "PRODUCTNAME";
    public static final Long PRODUCTID = 1L;
    public static final Long CATEID = 2L;
    /**
     *
     */
    
    List<Product> list;

    @BeforeEach
    public void setUpProduct() {
        list = new ArrayList<>();
        Category category = new Category(1L, "Laptop", "strong laptop");
        Product product = new Product("MSI RF", 17000000, "abcd.img", new Date(), new Date(), "productDescription", category, 100);
        Product product2 = new Product("MSI RG", 17000000, "abcd.img", new Date(), new Date(), "productDescription", category, 100);
        Product product3 = new Product("LENOVO LEGION", 17000000, "abcd.img", new Date(), new Date(), "productDescription", category, 100);
        list.add(product);
        list.add(product2);
        list.add(product3);
    }

    @Test
    public void getAllTest_returnProductList() throws Exception {
        when(productRepository.findAll()).thenReturn(list);
        assertEquals(productService.getAllProduct(null, null), list);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void whenValidID_thenProductShouldBeFound() throws Exception {

        Product product = new Product();
        Optional<Product> optional = Optional.of(product);
        assertNotNull(optional);
        when(productRepository.findById(PRODUCTID)).thenReturn(optional);
        ResponseEntity<Product> product2 = productService.getProductById(PRODUCTID);
        assertEquals(product2.getBody().getProductName(), product.getProductName());
    }
    @Test
    public void createProduct_ThenReturnProduct() throws Exception {
        Product product = new Product();
        List<Product> optional = new ArrayList<>();
        when(productRepository.findAllProductsByCateId(CATEID)).thenReturn(optional);
        equals(productService.createProduct(product , CATEID));
        
    }

    @Test
    public void updateProduct_ThenReturnNewProduct() throws Exception {
        Product newProduct = new Product();
        Optional<Product> optional = Optional.of(product);
        assertNotNull(optional);
        when(productRepository.findById(PRODUCTID)).thenReturn(optional);
        when(productRepository.save(optional.get())).thenReturn(product);
        ResponseEntity<Product> product2 = productService.updateProduct(newProduct, PRODUCTID);
        assertEquals(product2.getBody().getProductName(), newProduct.getProductName());
    }

    @Test
    public void whenValidID_thenDeleteProductShouldBeFound() throws Exception {

        Product Product = new Product();
        Optional<Product> optional = Optional.of(Product);
        assertNotNull(optional);
        when(productRepository.findById(PRODUCTID)).thenReturn(optional);
        Map<String, Boolean> product = productService.deleteProduct(PRODUCTID);
        assertEquals(product.equals(true), false);
    }

}
