package com.repository;

import com.beans.Client;
import com.beans.Project;
import com.testbuilder.ClientTestBuilder;
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

    private Project project;
    private Client client;

    @Before
    public void setUp() throws Exception {
        project = ProjectTestBuilder.project().build();
        client = ClientTestBuilder.client().build();
        project.setClient(client);
        client.getProjects().add(project);
        entityManager.persist(client);
    }

    @Test
    public void findOne() {
        assertThat(repository.findById(project.getId()).get()).isEqualTo(project);
    }
}
