package com.beans;

 import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@DiscriminatorValue("PROJECT")
public class Project extends Service {

    @NotEmpty
    @Column(name = "DESCRIPTION")
    private String description;

    @Valid
    @Embedded
    private Address address;

    @Valid
    @Embedded
    private Period period;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
