package com.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DiscriminatorValue("INVOICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends Document {

    @Embedded
    private TaxRate taxRate;

    @Column(name = "PAYMENT_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;

    @Override
    @NumberFormat(pattern = "###,##0.00")
    public BigDecimal subTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (DocumentLine documentLine : documentLines) {
            subTotal = subTotal.add(documentLine.getTotal());
        }
        return subTotal;
    }

    @Override
    @NumberFormat(pattern = "###,##0.00")
    public BigDecimal total() {
        return subTotal().add(subTotal().multiply(taxRate.getValue()));
    }

    @NumberFormat(pattern = "###,##0.00")
    public BigDecimal getTotalTax(){
        return subTotal().multiply(taxRate.getValue());
    }
}
