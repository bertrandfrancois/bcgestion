package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Entity(name = "CLIENTS")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    private Long id;

    @NotEmpty
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotEmpty
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Valid
    @Embedded
    private Address address;

    @NotEmpty
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotEmpty
    @Email
    @Column(name = "MAIL")
    private String mail;

    @Column(name = "TAX_NUMBER")
    private String taxNumber;

    @OneToMany(mappedBy = "client")
    private List<Project> projects;

    @OneToMany(mappedBy = "client")
    private List<Document> documents;
}