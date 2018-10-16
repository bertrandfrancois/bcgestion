package com.validation;

import com.model.Document;
import com.config.MockitoTest;
import com.repository.DocumentRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static org.mockito.Mockito.when;

public class UniqueCodeValidatorTest extends MockitoTest {

    private static final String CODE = "CODE";
    private static final long DOCUMENT_ID = 1L;
    private static final long OTHER_DOCUMENT_ID = 2L;

    @InjectMocks
    private UniqueCodeValidator uniqueCodeValidator;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private Document document, existingDocument;

    @Mock
    private ConstraintValidatorContext context;

    @Before
    public void setUp() {
        when(document.getId()).thenReturn(DOCUMENT_ID);
        when(document.getCode()).thenReturn(CODE);
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of());
    }

    @Test
    public void isValid() {
        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isTrue();
    }

    @Test
    public void isValid_DocumentCodeExist_SameDocumentId_ReturnsTrue() {
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of(existingDocument));
        when(existingDocument.getId()).thenReturn(DOCUMENT_ID);

        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isTrue();
    }

    @Test
    public void isValid_DocumentCodeExist_OtherDocumentId_ReturnsFalse() {
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of(existingDocument));
        when(existingDocument.getId()).thenReturn(OTHER_DOCUMENT_ID);

        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isFalse();
    }

    @Test
    public void isValid_DocumentCodeExist_NewDocument_ReturnsFalse() {
        when(document.getId()).thenReturn(null);
        when(documentRepository.findDocumentsByCode(CODE)).thenReturn(List.of(existingDocument));

        Assertions.assertThat(uniqueCodeValidator.isValid(document, context)).isFalse();
    }
}
