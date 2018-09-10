package com.beans;

import com.beans.validation.ClassCheck;
import com.beans.validation.ValidProject;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "PROJECTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidProject(groups =  ClassCheck.class)
@GroupSequence({Project.class, ClassCheck.class})
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
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @OneToMany(mappedBy = "project")
    private List<Document> documents = Lists.newArrayList();

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;
}
