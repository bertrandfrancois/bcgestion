package com.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity(name = "DOCUMENTS")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@Getter
@Setter
@NoArgsConstructor
public abstract class Document {

    @Id
    @GeneratedValue
    @Column(name = "DOCUMENT_ID")
    private Long id;

    @Column(name = "DOCUMENT_CODE")
    private String code;

    @Column(name = "CREATION_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @OneToMany
    @JoinColumn(name = "DOCUMENT_ID")
    protected List<DocumentLine> documentLines;

    public abstract BigDecimal subTotal();

    public abstract BigDecimal total();
}
