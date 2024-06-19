package com.ecom.phoenix.services;

import com.ecom.phoenix.dtos.*;
import com.ecom.phoenix.models.Product;
import com.ecom.phoenix.models.Stock;
import com.ecom.phoenix.repositories.ProductRepository;
import com.ecom.phoenix.repositories.StockRepository;
import com.ecom.phoenix.repositories.UserRepository;
import com.ecom.phoenix.utils.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    StockRepository stockRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DataSource dataSource;


    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }


    public Map<String, Object> findById(Long id) {

        Map<String, Object> product = productRepository.findByIdToShow(id).entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        product.put("size", productRepository.findSizeById(id));

        return product;
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

        Stock stock = this.stockRepository.findByProductId(product.getId());
        if (stock == null) {
            stock = new Stock();
        }
        stock.setProductId(product.getId());
        stock.setQuantity(productToShow.getQuantity());
        stock.setColor(productToShow.getColors());
        stock.setSize(productToShow.getSize());
        stock.setPrice(productToShow.getPrice());

        this.stockRepository.save(stock);

        return product;
    }

    @Transactional
    public ResponseEntity<Object> edit(Long id, ProductToShow newProduct) {
        Product productById = productRepository.findById(id);
        productById.setTeam(newProduct.getTeam());
        productById.setDescription(newProduct.getDescription());

        Stock stock = this.stockRepository.findByProductId(id);
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
    public ResponseEntity<Object> purchase(String userId, List<ProductsToPurchase> products) throws IOException {
        for (ProductsToPurchase product : products) {
            Stock oldProduct = this.stockRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (oldProduct.getQuantity() < product.getQuantity()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Quantidade do produto " + oldProduct.getId() + " acima da disponível");
            }
            oldProduct.setQuantity(oldProduct.getQuantity() - product.getQuantity());

            this.stockRepository.save(oldProduct);
        }

        ObjectMapper mapper = new ObjectMapper();
        EmailDto emailDto = new EmailDto();
        emailDto.setTo(this.userRepository.findById(userId).getEmail());
        emailDto.setSubject("Confirmação de Compra");
        emailDto.setBody("Olá,\n\nSua compra foi efetivada com sucesso! Agradecemos por escolher nossos serviços.\n\nDetalhes do pedido:\n- Produto: Nome do Produto\n- Quantidade: 1\n- Valor: R$ 100,00\n\nSeu pedido será processado em breve e você receberá uma notificação assim que for enviado.\n\nAtenciosamente,\nEquipe de Vendas");


        HttpPost post = new HttpPost("http://localhost:8080/api/email/send");
        post.setEntity(new StringEntity(mapper.writeValueAsString(emailDto), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(post)) {
                if (response.getCode() != 200) {
                    throw new RuntimeException("error while send email");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("error while send email", e);
        }

        return ResponseEntity.ok("Compra realizada com sucesso");
    }

    public String buildSqlQuery(ProductsFilter filters) {
        StringBuilder sql = new StringBuilder("SELECT p.*, s.price FROM products p " +
                "LEFT JOIN stock s ON s.product_id = p.id WHERE 0 = 0\n");

        for (Filter filter : filters.getFilters()) {
            sql.append("AND ");
            String field = filter.getField();
            Object value = filter.getValue();

            if ("team".equals(field)) {
                sql.append("p.team IN ('").append(String.join("', '", (List<String>) value)).append("') \n");
            } else if ("size".equals(field)) {
                sql.append("s.size IN ('").append(String.join("', '", (List<String>) value)).append("') \n");
            } else if ("colors".equals(field)) {
                sql.append("s.color ?| array['").append(String.join("', '", (List<String>) value)).append("'] \n");
            } else if ("price".equals(field)) {
                List<PriceRange> priceRanges = (List<PriceRange>) value;
                if (!priceRanges.isEmpty()) {
                    sql.append("(");
                    for (PriceRange priceRange : priceRanges) {
                        sql.append("p.price BETWEEN ").append(priceRange.getMin()).append(" AND ").append(priceRange.getMax()).append(" OR ");
                    }
                    sql.delete(sql.length() - 4, sql.length());
                    sql.append(") \n");
                }
            }
        }

        sql.append("GROUP BY p.id, s.color, s.price");

        Sort sort = filters.getSort();
        if (sort != null) {
            sql.append("\nORDER BY ").append(sort.getField()).append(" ").append(sort.getDir()).append(" \n");
        }

        return sql.toString();
    }


    public List<ProductToShow> getFilteredProducts(ProductsFilter filters) {
        String sqlQuery = buildSqlQuery(filters);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery);

        List<ProductToShow> list = new ArrayList<>();
        for (Map<String,Object> row : rows) {
            ProductToShow product = new ProductToShow();
            product.setId(Long.valueOf(String.valueOf(row.get("id"))));
            product.setTeam(String.valueOf(row.get("team")));
            product.setPrice(new BigDecimal(String.valueOf(row.get("price") )));
            product.setSize(String.valueOf(row.get("size")));

            list.add(product);
        }

        return list;
    }

    public Map<String, Object> filterOptions() {
        Map<String, Object> options = new HashMap<>();

        options.put("teams", productRepository.getAllTeams());
        options.put("color", productRepository.getAllColors());

        return options;
    }
}
