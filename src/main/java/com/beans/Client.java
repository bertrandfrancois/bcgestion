package com.beans;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity(name = "CLIENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "CLIENT_ID")
    private Long id;

    @NotEmpty
    @Size(max = 60)
    @Column(name = "LAST_NAME")
    private String lastName;

    @Size(max = 30)
    @Column(name = "FIRST_NAME")
    private String firstName;

    @Valid
    @Embedded
    private Address address;

    @NotEmpty
    @Size(max = 30)
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @NotEmpty
    @Email
    @Size(max = 30)
    @Column(name = "MAIL")
    private String mail;

    @Size(max = 30)
    @Column(name = "TAX_NUMBER")
    private String taxNumber;

    @OneToMany(mappedBy = "client", cascade= CascadeType.ALL)
    private List<Project> projects = Lists.newArrayList();

    @OneToMany(mappedBy = "client", cascade= CascadeType.ALL)
    private List<Document> documents = List.of();

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}