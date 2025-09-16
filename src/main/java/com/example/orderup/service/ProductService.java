package com.example.orderup.service;

import com.example.orderup.model.Product;
import com.example.orderup.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // New method to save product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
