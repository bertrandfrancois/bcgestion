package com.beans;

import com.beans.validation.ValidProject;
import com.google.common.collect.Lists;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidProject
@DiscriminatorValue("PROJECT")
public class Project extends Service{

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
