package com.validation;

import com.model.Document;
import com.repository.DocumentRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

public class UniqueCodeValidator implements ConstraintValidator<UniqueCode, Document> {

    private DocumentRepository documentRepository;

    public UniqueCodeValidator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void initialize(UniqueCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(Document document, ConstraintValidatorContext context) {
       return documentRepository
                .findDocumentsByCode(document.getCode())
                .stream()
                .filter(d -> !d.getId().equals(document.getId()))
                .collect(Collectors.toList())
                .isEmpty();
    }
}
