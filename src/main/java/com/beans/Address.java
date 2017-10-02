package com.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @NotEmpty
    @Size(max=50)
    @Column(name = "STREET")
    private String street;

    @NotEmpty
    @Size(max=5)
    @Column(name = "POST_CODE")
    private String postCode;

    @NotEmpty
    @Size(max=30)
    @Column(name = "CITY")
    private String city;

}