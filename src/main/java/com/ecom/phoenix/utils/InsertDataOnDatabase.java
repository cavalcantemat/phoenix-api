package com.ecom.phoenix.utils;

import com.ecom.phoenix.models.Product;
import com.ecom.phoenix.models.User;
import com.ecom.phoenix.services.ProductService;
import com.ecom.phoenix.services.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class InsertDataOnDatabase implements CommandLineRunner {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(InsertDataOnDatabase.class, args);
    }

    @Override
    public void run(String... args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/products.json")) {
            List<Product> products = objectMapper.readValue(inputStream, new TypeReference<>() {});

            products.stream()
                    .filter(product -> productService.findByTeam(product.getTeam()) == null)
                    .forEach(productService::save);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.userService.findByLogin("admin") == null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode("");
            User user = new User("admin", encryptedPassword, UserRole.ADMIN);
            this.userService.register(user);
        }
    }
}
