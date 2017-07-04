package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@DiscriminatorValue("INVOICE")
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends Document {

    @Embedded
    private TaxRate taxRate;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @Override
    public Price subTotal() {
        return null;
    }

    @Override
    public Price total() {
        return null;
    }
}
