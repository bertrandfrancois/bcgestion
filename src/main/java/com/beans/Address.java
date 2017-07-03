package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Address {

    @NotEmpty
    @Column(name = "STREET")
    private String street;

    @NotEmpty
    @Column(name = "POST_CODE")
    private String postCode;

    @NotEmpty
    @Column(name = "CITY")
    private String city;

}