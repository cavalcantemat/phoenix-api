package com.ecom.phoenix.repositories;

import com.ecom.phoenix.dtos.ProductToShow;
import com.ecom.phoenix.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(name = "findByIdToShow", value = "SELECT p.*, s.price, s.color FROM products p " +
            "LEFT JOIN stock s ON s.product_id = p.id " +
            "WHERE p.id = :id " +
            "GROUP BY p.id, s.price, s.color", nativeQuery = true)
    Map<String, Object> findByIdToShow(Long id);

    @Query(
            value = """
                    SELECT size, (quantity > 0 AND quantity IS NOT NULL) AS in_stock
                    FROM stock
                    WHERE product_id = :id
                    """,
            nativeQuery = true
    )
    List<Map<String, Object>> findSizeById(Long id);

    @Query(value = """
            SELECT DISTINCT team FROM products
            ORDER BY team
            """, nativeQuery = true)
    List<String> getAllTeams();

    @Query(value = """
            SELECT DISTINCT jsonb_array_elements_text(color) AS color FROM stock
            ORDER BY color
            """, nativeQuery = true)
    List<String> getAllColors();

    Product findById(Long id);
    Product findByTeam(String team);
}
