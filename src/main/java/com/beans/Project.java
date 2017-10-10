package com.beans;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "PROJECT_ID")
    private Long id;

    @NotEmpty
    @Column(name = "DESCRIPTION")
    private String description;

    @Valid
    @Embedded
    private Address address;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "START_DATE")
    private Date startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Column(name = "END_DATE")
    private Date endDate;

    @OneToMany(mappedBy = "project")
    private List<Document> documents = Lists.newArrayList();

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

}
