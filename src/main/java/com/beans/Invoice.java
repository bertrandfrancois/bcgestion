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
import java.time.LocalDate;
import java.util.Date;

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
