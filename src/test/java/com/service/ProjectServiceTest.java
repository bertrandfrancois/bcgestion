package com.service;

import com.beans.Project;
import com.repository.ProjectRepository;
import com.testbuilder.ProjectTestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectService.class)
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    private Project project;

    @Before
    public void setUp() throws Exception {
        project = ProjectTestBuilder.project().withId(1L).build();
    }

    @Test
    public void findAll() throws Exception {
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void find() throws Exception {
        given(projectRepository.findOne(1L)).willReturn(project);

        assertThat(projectService.find(1L)).isEqualTo(project);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void findLastProject() throws Exception {
        given(projectRepository.findTopByOrderByIdDesc()).willReturn(project);

        assertThat(projectService.findLastProject()).isEqualTo(project);

    }

}