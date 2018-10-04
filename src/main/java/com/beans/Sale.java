package com.beans;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@DiscriminatorValue("SALE")
@Data
public class Sale extends Service{

    @OneToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

}
