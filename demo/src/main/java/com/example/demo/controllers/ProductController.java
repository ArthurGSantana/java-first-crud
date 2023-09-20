package com.example.demo.controllers;

import com.example.demo.domain.product.Product;
import com.example.demo.domain.product.ProductRepository;
import com.example.demo.domain.product.RequestProduct;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        var products = productRepository.findAllByActiveTrue();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid RequestProduct product) {
        Product newProduct = new Product(product);
        var createdProduct = productRepository.save(newProduct);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid Product product) {
        Optional<Product> newProduct = productRepository.findById(product.getId());
        if (newProduct.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            newProduct.get().setName(product.getName());
            newProduct.get().setPrice(product.getPrice());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            Product newProduct = product.get();
            newProduct.setActive(false);
            return ResponseEntity.noContent().build();
        }
    }
}
