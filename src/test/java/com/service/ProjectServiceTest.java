package com.service;

import com.model.Project;
import com.config.MockitoTest;
import com.repository.ProjectRepository;
import com.testbuilder.ProjectTestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class ProjectServiceTest extends MockitoTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    private Project project;

    @Before
    public void setUp() throws Exception {
        project = ProjectTestBuilder.project().withId(1L).build();
    }

    @Test
    public void findAll() {
    }

    @Test
    public void save() {
    }

    @Test
    public void find() {
        given(projectRepository.findById(1L)).willReturn(Optional.of(project));

        assertThat(projectService.find(1L)).isEqualTo(project);
    }

    @Test
    public void delete() {
    }

}