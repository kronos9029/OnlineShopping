package com.phatpt.springExercise;

import com.phatpt.springExercise.entity.Category;
import com.phatpt.springExercise.repository.CategoryRepository;
import com.phatpt.springExercise.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest{

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

    List<Category> categories;
    // @BeforeEach
    // void setUp(){
    // this.categories = new ArrayList<>();
    // this.categories.add(new Category(1, "PC1", "ok"));
    // this.categories.add(new Category(2, "PC2", "ok"));
    // // this.categories.add(new Category(3, "PC3", "ok"));
    // }

    @Test
    // @WithMockUser(username ="ROLE_CUSTOMER")
    public void getAllTest() throws Exception {
        this.categories = new ArrayList<>();
        this.categories.add(new Category("PC2", "ok"));
        this.categories.add(new Category("PC3", "ok"));
        this.categories.add(new Category("PC4", "ok"));
        System.out.println(categories);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);
        this.mockMvc.perform(get("/categories/")).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void getAllCategoryAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.categories").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[*].categoryID").isNotEmpty());
    }

    @Test
    public void getCategoryByIdAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.categoryID").value(1));
    }

    @Test
    public void createEmployeeAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/categories/").content(asJsonString(new Category("PC2", "ok")))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.categoryID").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateCategoryAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/{id}", 1)
                .content(asJsonString(new Category("Laptop", "okla"))).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.PC2").value("Laptop"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.ok").value("okla"));
    }

    @Test
    public void deleteCategoryAPI() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", 1)).andExpect(status().isAccepted());
    }
}
