package com.phatpt.springExercise.ProductTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Repository.ProductRepository;
import com.phatpt.springExercise.Service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductTestService {
    @Autowired
    private ProductService productService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    Product product;

    @Test
    public void getAllTest() throws Exception {
        List<Product> list = new ArrayList<>();
        Product product1 = new Pro
        Product product2 = new Product("MSI gaming", "alsoha", new Date(), new Date(), 22000, "alive", "dwsssan.img", 9);
        list.add(product1);
        list.add(product2);
        when(productRepository.findAll()).thenReturn(list);
        List<Product> listProducts = productService.getAllProduct();
        assertEquals(2, listProducts.size());
    }

    @Test
    public void saveProduct() throws Exception {
        Long id = 2L;
        String ProductName = "ASUS";
        String ProductDiscription = "ok formal";
        float ProductPrice = 1550;
        String setProductImage = "asdd.img";
        int ProductQuantity = 11;
        product.setProductId(id);
        product.setProductName(ProductName);
        product.setProductDescription(ProductDiscription);
        product.setProductPrice(ProductPrice);
        product.setImage(setProductImage);
        product.setQuantity(ProductQuantity);

        assertEquals(productService.createProduct(product), null);
    }
    @Test
    public void findByID() throws Exception{
    Product product = new Product();
    product.setProductName("Auss gaming");
    product.setProductDescription("nice");
    product.setCreateDate(new Date());
    product.setProductPrice(15000);
    product.setImage("dwjakjdkwaj");
    product.setQuantity(11);

    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    assertEquals(productService.getProductById(1L), 1);

    }
}
