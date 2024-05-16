package com.ecom.phoenix.controllers;

import com.ecom.phoenix.models.Product;
import com.ecom.phoenix.services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() throws IOException {
        return productService.getProducts();
    }

    @PostMapping("/list")
    public List<Product> getFilteredProducts(@RequestBody JsonNode params) throws IOException {
        return productService.getFilteredProducts(params);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Integer id) {
        return productService.getById(id);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Product product) {
        Product savedProduct = this.productService.save(product);

        return ResponseEntity.ok(savedProduct.getId());
    }
}
