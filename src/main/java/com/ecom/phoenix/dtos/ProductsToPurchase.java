package com.ecom.phoenix.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsToPurchase {
    private Long id;
    private Long quantity;
    private String size;
}