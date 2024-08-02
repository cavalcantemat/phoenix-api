package com.ecom.phoenix.dtos;

import java.math.BigDecimal;

public class PriceRange {
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }
}