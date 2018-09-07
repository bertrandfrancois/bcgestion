package com.beans;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
public class Price {

    @Column(name = "PRICE")
    private BigDecimal price;

    public Price(BigDecimal price) {
        this.price = price;
    }
}
