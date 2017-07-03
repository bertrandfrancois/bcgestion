package com.enums;

public enum DocumentType {
    ESTIMATE("ESTIMATE"),
    INVOICE("INVOICE");

    private String type;

    private DocumentType(String type) {
        this.type = type;
    }
}
