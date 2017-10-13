package com.repository;

import com.beans.Project;
import com.testbuilder.ProjectTestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProjectRepository repository;

    private Project project, otherProject;

    @Before
    public void setUp() throws Exception {
        project = ProjectTestBuilder.project().build();
        otherProject = ProjectTestBuilder.project().build();
        entityManager.persist(project);
        entityManager.persist(otherProject);
    }

    @Test
    public void findOne() throws Exception {
        assertThat(repository.findOne(project.getId())).isEqualTo(project);
    }

    @Test
    public void findTopByOrderByIdDesc() throws Exception {
        assertThat(repository.findTopByOrderByIdDesc()).isEqualTo(otherProject);
    }

}