package com.phatpt.springExercise.Controller;

import java.util.List;
import java.util.Optional;

import com.phatpt.springExercise.Constant.ErrorCode;
import com.phatpt.springExercise.Constant.SuccessCode;
import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Entity.Response;
import com.phatpt.springExercise.Service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    private final CategoryService categoryService;

    private Response responseObj;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public ResponseEntity<Response> getAllCategories() {
        try {
            List<Category> listCate = categoryService.getAllCategories();
            if (listCate.isEmpty()) {
                responseObj.setErrorCode(ErrorCode.CATEGORY_FIND_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.CATEGORY_FIND_SUCCESS);
                responseObj.setData(listCate);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CATEGORY_FIND_ERROR);
            throw new RuntimeException("ERROR AT CategoryController: " + e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @GetMapping("/{cateId}")
    public ResponseEntity<Response> getCateById(@PathVariable(value = "cateId") Long cateId) {
        try {
            Optional<Category> category = categoryService.getCateById(cateId);
            if (category.isEmpty()) {
                responseObj.setErrorCode(ErrorCode.CATEGORY_FIND_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.CATEGORY_FIND_SUCCESS);
                responseObj.setData(category);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CATEGORY_FIND_ERROR);
            throw new RuntimeException("ERROR AT CategoryController: " + e.getMessage());
        }
        return ResponseEntity.ok().body(responseObj);
    }

    @PostMapping("/")
    public ResponseEntity<Response> createCate(@RequestBody Category newCate) {
        try {
            Category category = categoryService.createCate(newCate);
            responseObj.setSuccessCode(SuccessCode.CATEGORY_ADD_SUCCESS);
            responseObj.setData(category);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CATEGORY_ADD_ERROR);
            throw new RuntimeException("ERROR AT CategoryController: " + e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<Response> updateCategory(@RequestBody Category cateDetail,
            @PathVariable(value = "cateId") Long cateId) {
        try {
            Category category = categoryService.updateCategory(cateDetail, cateId);
            responseObj.setSuccessCode(SuccessCode.CATEGORY_UPDATE_SUCCESS);
            responseObj.setData(category);
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CATEGORY_UPDATE_ERROR);
            throw new RuntimeException("ERROR AT CategoryController: " + e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

    @DeleteMapping("/{cateId}")
    public ResponseEntity<Response> deleteCate(@PathVariable(value = "cateId") Long cateId) {
        try {
            if (cateId == null || categoryService.deleteCate(cateId) == false) {
                responseObj.setErrorCode(ErrorCode.CATEGORY_DELETE_ERROR);
            } else {
                responseObj.setSuccessCode(SuccessCode.CATEGORY_DELETE_SUCCESS);
            }
        } catch (Exception e) {
            responseObj.setErrorCode(ErrorCode.CATEGORY_DELETE_ERROR);
            throw new RuntimeException("ERROR AT CategoryController: " + e.getMessage());
        }

        return ResponseEntity.ok().body(responseObj);
    }

}
