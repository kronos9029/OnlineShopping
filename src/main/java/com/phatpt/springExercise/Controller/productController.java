package com.phatpt.springExercise.Controller;

import java.util.List;

import com.phatpt.springExercise.Constant.ErrorCode;
import com.phatpt.springExercise.Constant.SuccessCode;
import com.phatpt.springExercise.Entity.Product;
import com.phatpt.springExercise.Entity.Response;
import com.phatpt.springExercise.Service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    private Response responseObj;

    @Autowired
    public ProductController(com.phatpt.springExercise.Service.ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<Response> getAllProduct(){
        try {
            List<Product> productList = productService.getAllProduct();
            responseObj.setSuccessCode(SuccessCode.PRODUCT_GET_SUCCESS);
            responseObj.setData(productList);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_GET_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Response> getProductById(@PathVariable(value = "productId") Long productId) {
        try {
            Product product = productService.getProductById(productId);
            responseObj.setSuccessCode(SuccessCode.PRODUCT_GET_SUCCESS);
            responseObj.setData(product);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_GET_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PostMapping("")
    public ResponseEntity<Response> createProduct(@RequestBody Product newProduct, @RequestParam("cateId") long cateId) throws Exception{
        try {
            Product product = productService.createProduct(newProduct, cateId);
            responseObj.setSuccessCode(SuccessCode.PRODUCT_CREATE_SUCCESS);
            responseObj.setData(product);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_ADD_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PutMapping("")
    public ResponseEntity<Response> updateProduct(@RequestBody Product productDetail, @RequestParam(value = "id") Long productId){
        try {
            Product product = productService.updateProduct(productDetail, productId);
            responseObj.setSuccessCode(SuccessCode.PRODUCT_UPDATE_SUCCESS);
            responseObj.setData(product);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_UPDATE_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<Response> deleteProduct(@PathVariable(value = "productId") Long productId){
        try {
            if(productService.deleteProduct(productId) != true){
                responseObj.setErrorCode(ErrorCode.PRODUCT_DELETE_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.PRODUCT_DELETE_SUCCESS);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_DELETE_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }
    
    @GetMapping("/ByCate")
    @ResponseBody
    public ResponseEntity<Response> getAllProductsByCateId(@RequestParam("cateId") Long cateId){
        try {
            List<Product> productList = productService.findAllProductsByCateId(cateId);
            if(productList.isEmpty()){
                responseObj.setErrorCode(ErrorCode.PRODUCT_FIND_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.PRODUCT_FIND_SUCCESS);
                responseObj.setData(productList);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_FIND_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @GetMapping("/ByName")
    public ResponseEntity<Response> getProductByName(@RequestParam("productName") String productName) throws Exception{
        try {
            List<Product> productList = productService.findProductByName(productName);
            if(productList.isEmpty()){
                responseObj.setErrorCode(ErrorCode.PRODUCT_FIND_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.PRODUCT_FIND_SUCCESS);
                responseObj.setData(productList);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.PRODUCT_FIND_ERROR);
            throw new RuntimeException("ERROR AT ProductController: "+ e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }
}
