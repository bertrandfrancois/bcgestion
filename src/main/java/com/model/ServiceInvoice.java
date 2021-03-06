package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("SERVICE_INVOICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInvoice extends Document {

    @Column(name = "PAYMENT_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

}
