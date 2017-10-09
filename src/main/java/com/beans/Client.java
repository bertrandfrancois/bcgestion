package com.beans;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "CLIENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    private Long id;

    @NotEmpty
    @Size(max=30)
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotEmpty
    @Size(max=30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Valid
    @Embedded
    private Address address;

    @NotEmpty
    @Size(max=30)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotEmpty
    @Email
    @Size(max=30)
    @Column(name = "MAIL")
    private String mail;

    @Size(max=30)
    @Column(name = "TAX_NUMBER")
    private String taxNumber;

    @OneToMany(mappedBy = "client")
    private List<Project> projects = Lists.newArrayList();

    @OneToMany(mappedBy = "client")
    private List<Document> documents = Lists.newArrayList();
}