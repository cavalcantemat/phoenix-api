package com.ecom.phoenix.repositories;

import com.ecom.phoenix.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findById(Long id);
    Product findByTeam(String team);
}
