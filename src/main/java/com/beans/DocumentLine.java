package com.beans;


import com.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity(name = "DOCUMENT_LINES")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentLine {

    @Id
    @GeneratedValue
    @Column(name = "DOCUMENT_LINE_ID")
    private int id;

    @Column(name = "DESCRIPTION")
    @NotEmpty
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Unit unit;

    @NumberFormat(pattern = "###,##0.000")
    @NotNull
    private BigDecimal quantity;

    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    @NotNull
    private BigDecimal price;

    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    public BigDecimal getTotal(){
        return price.multiply(quantity);
    }
}
