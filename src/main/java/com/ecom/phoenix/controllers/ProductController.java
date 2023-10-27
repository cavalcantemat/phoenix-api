package com.ecom.phoenix.controllers;

import com.ecom.phoenix.repositories.Product;
import com.ecom.phoenix.services.ProductService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getProducts();
    }

    @PostMapping("/products/list")
    public List<Product> getFilteredProducts(@RequestBody JsonNode params) {
        System.out.println(params);
        return productService.getFilteredProducts(params);
    }

    @GetMapping("/products/{id}")
    public Product getById(@PathVariable Integer id) {
        return productService.getById(id);
    }
}
