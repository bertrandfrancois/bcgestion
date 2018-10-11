package com.beans.validation;

import com.beans.Document;
import com.repository.DocumentRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

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
        Optional<Document> existingDocument = documentRepository.findByCode(document.getCode());
        return !existingDocument.isPresent() ||
               (document.getId() != null && existingDocument.get().getId().equals(document.getId()));
    }
}
