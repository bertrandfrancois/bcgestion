package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("INVOICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends Document {

    @Column(name = "PAYMENT_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

}
