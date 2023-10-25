package com.ecom.phoenix.services;

import com.ecom.phoenix.repositories.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ProductService {

    public List<Product> getProducts() {
        return getAllProducts();
    }

    private List<Product> getAllProducts() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json");

        try {
            return objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Product getById(Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Product>> typeReference = new TypeReference<>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json");

        try {
            List<Product> products = objectMapper.readValue(inputStream, typeReference);
            Product foundProduct = null;

            for (Product product : products) {
                if (product.getId() == id) {
                    foundProduct = product;
                    break;
                }
            }

            return foundProduct;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
