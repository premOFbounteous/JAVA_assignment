package com.example.orderup.service;

import com.example.orderup.exception.InsufficientStockException;
import com.example.orderup.exception.ProductNotFoundException;
import com.example.orderup.model.Order;
import com.example.orderup.model.Product;
import com.example.orderup.repository.OrderRepository;
import com.example.orderup.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testPlaceOrder() {
        Product product = new Product();
        product.setId(1L);
        product.setStock(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        orderService.placeOrder(1L, 1);
    }

    @Test
    public void testPlaceOrder_productNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> orderService.placeOrder(1L, 1));
    }

    @Test
    public void testPlaceOrder_insufficientStock() {
        Product product = new Product();
        product.setId(1L);
        product.setStock(0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> orderService.placeOrder(1L, 1));
    }
}