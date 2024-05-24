package com.ecom.phoenix.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter

public class PriceRange {
    private BigDecimal min;
    private BigDecimal max;

}