package com.beans;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Embeddable
@Data
public class TaxRate {

    @Column(name = "TAX_RATE")
    @NumberFormat(style= NumberFormat.Style.PERCENT)
    private BigDecimal value;

    @Override
    public String toString() {
        return value.multiply(BigDecimal.valueOf(100L)).toString() + " %";
    }
}
