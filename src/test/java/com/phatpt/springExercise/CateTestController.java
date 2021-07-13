package com.phatpt.springExercise;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.phatpt.springExercise.Entity.Category;
import com.phatpt.springExercise.Repository.CategoryRepository;
import com.phatpt.springExercise.Service.CategoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CateTestController {
    @Autowired
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;
    @Test
    public void getAllTest() throws Exception {
        List<Category> list = new ArrayList<>();
        Category cate1 = new Category(1,"PC1", "ok");
        Category cate2 = new Category(2,"PC2", "ok");
        list.add(cate1);
        list.add(cate2);
        when(categoryRepository.findAll()).thenReturn(list);    
        List<Category> categoriess = categoryService.getAllCategories();
        assertEquals(2,categoriess.size());
    }
}
