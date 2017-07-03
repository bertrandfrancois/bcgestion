package com.beans;

import com.enums.DocumentType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity(name = "DOCUMENT")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@Getter
@Setter
@NoArgsConstructor
public abstract class Document {

    @Id
    @Column(name = "DOCUMENT_ID")
    private Long id;

    @Column(name = "TYPE")
    private DocumentType documentType;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "PAYMENT_DATE")
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @OneToMany
    @JoinColumn(name = "DOCUMENT_ID")
    private List<DocumentLine> documentLines;

    public abstract Price subTotal();

    public abstract Price total();
}
