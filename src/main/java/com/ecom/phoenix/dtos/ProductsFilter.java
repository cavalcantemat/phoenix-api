package com.ecom.phoenix.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductsFilter {
    private List<Filter> filters;
    private Sort sort;
}
