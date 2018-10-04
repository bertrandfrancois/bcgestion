package com.beans;

import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name="SERVICES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@Data
@DiscriminatorOptions(force = true)
public abstract class Service {

    @Id
    @GeneratedValue
    @Column(name = "SERVICE_ID")
    private Long id;

    @OneToMany(mappedBy = "service")
    private List<Document> documents = Lists.newArrayList();
}
