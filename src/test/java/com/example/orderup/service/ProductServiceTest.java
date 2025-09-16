package com.example.orderup.service;

import com.example.orderup.model.Product;
import com.example.orderup.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Laptop");
        p1.setStock(10);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Mouse");
        p2.setStock(20);

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_found() {
        Product p = new Product();
        p.setId(1L);
        p.setName("Laptop");
        p.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(p));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_notFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(99L);

        assertNull(result);
        verify(productRepository, times(1)).findById(99L);
    }

    @Test
    void testSaveProduct() {
        Product p = new Product();
        p.setName("Keyboard");
        p.setStock(5);

        when(productRepository.save(any(Product.class))).thenReturn(p);

        Product saved = productService.saveProduct(p);

        assertNotNull(saved);
        assertEquals("Keyboard", saved.getName());
        assertEquals(5, saved.getStock());
        verify(productRepository, times(1)).save(p);
    }
}
