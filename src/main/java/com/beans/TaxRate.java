package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class TaxRate {

    @Column(name = "TAX_RATE")
    @NumberFormat(style= NumberFormat.Style.PERCENT)
    private BigDecimal value;
}
