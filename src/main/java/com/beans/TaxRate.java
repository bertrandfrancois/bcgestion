package com.beans;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class TaxRate {

    @Column(name = "TAX_RATE")
    private BigDecimal taxRate;
}
