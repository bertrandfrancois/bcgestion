package com.beans;


import com.enums.Unit;

import javax.persistence.*;

@Entity(name = "DOCUMENT_LINES")
public class DocumentLine {

    @Id
    @GeneratedValue
    @Column(name = "DOCUMENT_LINE_ID")
    private int id;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Price price;
}
