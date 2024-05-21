package com.ecom.phoenix.repositories;

import com.ecom.phoenix.dtos.ProductToShow;
import com.ecom.phoenix.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(name = "findById", value = "SELECT * FROM products p " +
            "LEFT JOIN stock s ON s.product_id = p.id " +
            "WHERE p.id = :id ", nativeQuery = true)
    ProductToShow findByIdToShow(Long id);

    Product findById(Long id);
    Product findByTeam(String team);
}
