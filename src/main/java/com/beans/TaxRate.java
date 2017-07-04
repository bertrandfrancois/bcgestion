package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TaxRate {

    @Column(name = "TAX_RATE")
    private BigDecimal taxRate;
}
