package com.ecom.phoenix.services;

import com.ecom.phoenix.models.Product;
import com.ecom.phoenix.repositories.ProductRepository;
import com.ecom.phoenix.utils.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public List<Product> getFilteredProducts(JsonNode params) {
        List<Product> allProducts = this.productRepository.findAll();

        JsonNode teamsFilters = params.get("teamsFilters");
        JsonNode leagueFilters = params.get("leaguesFilters");
        JsonNode colorsFilters = params.get("colorsFilters");
        JsonNode orderBy = params.get("orderBy");

        if (teamsFilters.isEmpty() && leagueFilters.isEmpty() && colorsFilters.isEmpty() & orderBy == null) {
            sortBy(allProducts, "");
            return allProducts;
        }

        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : allProducts) {
            boolean teamMatch = false;
            boolean leagueMatch = false;
            boolean colorMatch = false;

            if (leagueFilters.isArray() && !leagueFilters.isEmpty()) {
                String productLeague = product.getLeague();
                for (JsonNode league : leagueFilters) {
                    if (league.isTextual() && league.asText().equals(productLeague)) {
                        leagueMatch = true;
                        break;
                    }
                }
            } else {
                leagueMatch = true;
            }

            if (teamsFilters.isArray() && !teamsFilters.isEmpty()) {
                String productTeam = product.getDirectory();
                for (JsonNode teamNode : teamsFilters) {
                    if (teamNode.isTextual() && teamNode.asText().equals(productTeam)) {
                        teamMatch = true;
                        break;
                    }
                }
            } else {
                teamMatch = true;
            }

            if (colorsFilters.isArray() && !colorsFilters.isEmpty()) {
                List<String> productColors = new ArrayList<>(product.getColor());

                for (JsonNode colorNode : colorsFilters) {
                    String filterColor = colorNode.asText();
                    if (colorNode.isTextual() && productColors.contains(filterColor)) {
                        colorMatch = true;
                        break;
                    }
                }
            } else {
                colorMatch = true;
            }

            if (teamMatch && leagueMatch && colorMatch) {
                filteredProducts.add(product);
            }
        }

        if (orderBy != null && orderBy.isTextual()) {
            String orderByValue = orderBy.asText();
            sortBy(filteredProducts, orderByValue);
        }

        return filteredProducts;
    }

    private void sortBy(List<Product> products, String orderBy) {
        switch (orderBy) {
            case "name":
                products.sort(Comparator.comparing(Product::getTeam));
                break;
            case "low-price":
                products.sort(Comparator.comparing(Product::getPrice));
                break;
            case "biggest-price":
                products.sort(Comparator.comparing(Product::getPrice).reversed());
                break;
            default:
                products.sort(Comparator.comparing(Product::getId));
        }
    }

    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    public Product findByTeam(String team) {
        return this.productRepository.findByTeam(team);
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Transactional
    public Product edit(Long id, Product newProduct) {
        return productRepository.findById(id)
            .map(product -> {
                product.setTeam(newProduct.getTeam());
                product.setColor(newProduct.getColor());
                product.setCategory(newProduct.getCategory());
                product.setLeague(newProduct.getLeague());
                product.setPrice(newProduct.getPrice());
                product.setStorage(newProduct.getStorage());
                product.setDescription(newProduct.getDescription());
                product.setDirectory(newProduct.getDirectory());

                return productRepository.save(product);
            })
            .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }
}
