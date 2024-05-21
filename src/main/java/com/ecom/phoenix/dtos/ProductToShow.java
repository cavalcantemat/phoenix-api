package com.ecom.phoenix.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductToShow {
    private Long id;
    private String team;
    private Long quantity;
    private String size;
    private List<String> colors;
    private String description;
    private BigDecimal price;
}