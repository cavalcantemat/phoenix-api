package com.ecom.phoenix.repositories;

import com.ecom.phoenix.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Optional<Stock> findById(Long id);

    Stock findByProductId(Long id);
}