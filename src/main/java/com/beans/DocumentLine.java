package com.beans;


import com.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
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
    private String description;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @NumberFormat(pattern = "###,##0.000")
    private BigDecimal quantity;

    @NumberFormat(pattern = "###,##0.00")
    private BigDecimal price;

    public BigDecimal getTotal(){
        return price.multiply(quantity);

    }
}
