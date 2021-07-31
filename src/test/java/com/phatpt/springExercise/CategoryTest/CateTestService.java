package com.phatpt.springExercise.CategoryTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.phatpt.springExercise.entity.Category;
import com.phatpt.springExercise.repository.CategoryRepository;
import com.phatpt.springExercise.service.CategoryService;

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
public class CateTestService {
    @Autowired
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    Category category;
    public static final String CATENAME = "CATENAME";
    // @InjectMocks
    // UserController uc;
    List<Category> list;

    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        Category cate1 = new Category("PC1", "ok");
        Category cate2 = new Category("PC2", "ok");
        Category cate3 = new Category( "PC2", "ok");
        list.add(cate1);
        list.add(cate2);
        list.add(cate3);
    }

    @Test
    public void getAllTest_returnCateList() throws Exception {
        when(categoryRepository.findAll()).thenReturn(list);
        assertEquals(categoryService.getAllCategories(), list);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void whenValidID_thenCategoryShouldBeFound() throws Exception {

        Category cate = new Category();
        Long CateID = 1L;
        Optional<Category> optional = Optional.of(cate);
        assertNotNull(optional);
        when(categoryRepository.findById(CateID)).thenReturn(optional);
        Category cate2 = categoryService.getCateById(CateID);
        assertEquals(cate2.getCateName(), cate.getCateName());
    }
    @Test
    public void saveCate() throws Exception {
        when(categoryRepository.save(list.get(0))).thenReturn(list.get(0));
        assertEquals(categoryService.createCate(list.get(0)), list.get(0));
    }

    @Test
    public void updateCate() throws Exception {
        Category cate = new Category();
        Long CateID = 1L;
        Optional<Category> optional = Optional.of(cate);
        assertNotNull(optional);
        when(categoryRepository.findById(CateID)).thenReturn(optional);
        when(categoryRepository.save(optional.get())).thenReturn(cate);
        ResponseEntity<Category> cate2 = categoryService.updateCategory(cate, CateID);
        assertEquals(cate2.getBody().getCateName(), cate.getCateName());
    }

    @Test
    public void whenValidID_thenDeleteCategoryShouldBeFound() throws Exception {

        Category cate = new Category();
        Long CateID = 1L;
        Optional<Category> optional = Optional.of(cate);
        assertNotNull(optional);
        when(categoryRepository.findById(CateID)).thenReturn(optional);
        Map<String, Boolean> cate2 = categoryService.deleteCate(CateID);
        assertEquals(cate2.equals(true), false);

    }
}
