package com.ecom.phoenix.services;

import com.ecom.phoenix.dtos.ProductToShow;
import com.ecom.phoenix.dtos.ProductsToPurchase;
import com.ecom.phoenix.models.Product;
import com.ecom.phoenix.models.Stock;
import com.ecom.phoenix.repositories.ProductRepository;
import com.ecom.phoenix.repositories.StockRepository;
import com.ecom.phoenix.utils.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    StockRepository stockRepository;

    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }


    public ProductToShow findById(Long id) {
        return this.productRepository.findByIdToShow(id);
    }

    public Product findByTeam(String team) {
        return this.productRepository.findByTeam(team);
    }


    @Transactional
    public Product create(ProductToShow productToShow) {
        Product product = productRepository.findById(productToShow.getId());
        if (product == null) {
            product = new Product();
        }

        product.setTeam(productToShow.getTeam());
        product.setDescription(productToShow.getDescription());

        product = productRepository.save(product);

        Stock stock = stockRepository.findByProductId(product.getId());
        if (stock == null) {
            stock = new Stock();
        }
        stock.setProductId(product.getId());
        stock.setQuantity(productToShow.getQuantity());
        stock.setColor(productToShow.getColors());
        stock.setSize(productToShow.getSize());
        stock.setPrice(productToShow.getPrice());

        stockRepository.save(stock);

        return product;
    }

    @Transactional
    public ResponseEntity<Object> edit(Long id, ProductToShow newProduct) {
        Product productById = productRepository.findById(id);
        productById.setTeam(newProduct.getTeam());
        productById.setDescription(newProduct.getDescription());

        Stock stock = stockRepository.findByProductId(id);
        stock.setSize(newProduct.getSize());
        stock.setQuantity(newProduct.getQuantity());
        stock.setPrice(newProduct.getPrice());
        stock.setColor(newProduct.getColors());

        try {
            this.productRepository.save(productById);
            this.stockRepository.save(stock);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Product not found: " + e);
        }

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity<Object> purchase(List<ProductsToPurchase> products) {
        //todo integrate email service
        for (ProductsToPurchase product : products) {
            Stock oldProduct = stockRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (oldProduct.getQuantity() < product.getQuantity()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Quantidade do produto " + oldProduct.getId() + " acima da disponível");
            }
            oldProduct.setQuantity(oldProduct.getQuantity() - product.getQuantity());

            stockRepository.save(oldProduct);
        }


        return ResponseEntity.ok("Compra realizada com sucesso");
    }

    public List<Product> getFilteredProducts(JsonNode params) {
        List<Product> allProducts = this.productRepository.findAll();
        return allProducts;
//        JsonNode teamsFilters = params.get("teamsFilters");
//        JsonNode leagueFilters = params.get("leaguesFilters");
//        JsonNode colorsFilters = params.get("colorsFilters");
//        JsonNode orderBy = params.get("orderBy");
//
//        if (teamsFilters.isEmpty() && leagueFilters.isEmpty() && colorsFilters.isEmpty() & orderBy == null) {
//            sortBy(allProducts, "");
//            return allProducts;
//        }
//
//        List<Product> filteredProducts = new ArrayList<>();
//        for (Product product : allProducts) {
//            boolean teamMatch = false;
//            boolean leagueMatch = false;
//            boolean colorMatch = false;
//
//            if (leagueFilters.isArray() && !leagueFilters.isEmpty()) {
//                String productLeague = product.getLeague();
//                for (JsonNode league : leagueFilters) {
//                    if (league.isTextual() && league.asText().equals(productLeague)) {
//                        leagueMatch = true;
//                        break;
//                    }
//                }
//            } else {
//                leagueMatch = true;
//            }
//
//            if (teamsFilters.isArray() && !teamsFilters.isEmpty()) {
//                String productTeam = product.getDirectory();
//                for (JsonNode teamNode : teamsFilters) {
//                    if (teamNode.isTextual() && teamNode.asText().equals(productTeam)) {
//                        teamMatch = true;
//                        break;
//                    }
//                }
//            } else {
//                teamMatch = true;
//            }
//
//            if (colorsFilters.isArray() && !colorsFilters.isEmpty()) {
//                List<String> productColors = new ArrayList<>(product.getColor());
//
//                for (JsonNode colorNode : colorsFilters) {
//                    String filterColor = colorNode.asText();
//                    if (colorNode.isTextual() && productColors.contains(filterColor)) {
//                        colorMatch = true;
//                        break;
//                    }
//                }
//            } else {
//                colorMatch = true;
//            }

//            if (teamMatch && leagueMatch && colorMatch) {
//                filteredProducts.add(product);
//            }
//        }
//
//        if (orderBy != null && orderBy.isTextual()) {
//            String orderByValue = orderBy.asText();
//            sortBy(filteredProducts, orderByValue);
//        }
//
//        return filteredProducts;
    }

    private void sortBy(List<Product> products, String orderBy) {
//        switch (orderBy) {
//            case "name":
//                products.sort(Comparator.comparing(Product::getTeam));
//                break;
//            case "low-price":
//                products.sort(Comparator.comparing(Product::getPrice));
//                break;
//            case "biggest-price":
//                products.sort(Comparator.comparing(Product::getPrice).reversed());
//                break;
//            default:
//                products.sort(Comparator.comparing(Product::getId));
//        }
    }
}
