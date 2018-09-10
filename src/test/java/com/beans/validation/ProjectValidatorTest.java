package com.beans.validation;

import com.beans.Project;
import com.config.MockitoTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ProjectValidatorTest extends MockitoTest {

    @InjectMocks
    private ProjectValidator projectValidator;

    @Mock
    private Project project;

    @Mock
    private ConstraintValidatorContext constraint;

    @Test
    public void isValid() {

        given(project.getStartDate()).willReturn(LocalDate.of(2018, 1, 1));
        given(project.getEndDate()).willReturn(LocalDate.of(2018, 1, 2));

        assertThat(projectValidator.isValid(project, constraint)).isTrue();
    }

    @Test
    public void isValid_SameDate() {

        given(project.getStartDate()).willReturn(LocalDate.of(2018, 1, 1));
        given(project.getEndDate()).willReturn(LocalDate.of(2018, 1, 1));

        assertThat(projectValidator.isValid(project, constraint)).isTrue();
    }

    @Test
    public void isValid_EndDateBeforeStartDate_ReturnsFalse() {

        given(project.getStartDate()).willReturn(LocalDate.of(2018, 1, 2));
        given(project.getEndDate()).willReturn(LocalDate.of(2018, 1, 1));

        assertThat(projectValidator.isValid(project, constraint)).isFalse();
    }
}