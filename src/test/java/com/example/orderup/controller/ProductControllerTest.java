package com.example.orderup.controller;

import com.example.orderup.model.Product;
import com.example.orderup.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProducts() throws Exception {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Laptop");
        p1.setStock(10);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Mouse");
        p2.setStock(20);

        List<Product> products = Arrays.asList(p1, p2);
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Mouse"));
    }

    @Test
    void testGetProductById_found() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");
        p.setStock(10);

        Mockito.when(productService.getProductById(1L)).thenReturn(p);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void testGetProductById_notFound() throws Exception {
        Mockito.when(productService.getProductById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateProduct() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");
        p.setStock(10);

        Mockito.when(productService.saveProduct(any(Product.class))).thenReturn(p);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.stock").value(10));
    }
}
