package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("INVOICE")
@Getter
@Setter
@NoArgsConstructor
public class Invoice extends Document {

    @Embedded
    private TaxRate taxRate;

    @Override
    public Price subTotal() {
        return null;
    }

    @Override
    public Price total() {
        return null;
    }
}
