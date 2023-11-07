package com.ecom.phoenix.services;

import com.ecom.phoenix.repositories.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

@Service
public class ProductService {

    public List<Product> getProducts() throws IOException {
        return getAllProducts();
    }

    private List<Product> getAllProducts() throws IOException {
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


    // TODO: 27/10/2023 search, filters, get all colors

    public List<Product> getFilteredProducts(JsonNode params) throws IOException {
        List<Product> allProducts = this.getAllProducts();

        JsonNode teamsFilters = params.get("teamsFilters");
        JsonNode leagueFilters = params.get("leaguesFilters");
        JsonNode colorsFilters = params.get("colorsFilters");

        if (teamsFilters.isEmpty() && leagueFilters.isEmpty() && colorsFilters.isEmpty()) {
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
                List<ArrayNode> productColors = Arrays.asList(product.getColor());
                for (JsonNode colorNode : colorsFilters) {
                    if (colorNode.isTextual() && productColors.contains(colorNode.asText())) {
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
        return filteredProducts;
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
