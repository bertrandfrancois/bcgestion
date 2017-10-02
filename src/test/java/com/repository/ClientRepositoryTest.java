package com.repository;

import com.beans.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.testbuilder.ClientTestBuilder.client;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository repository;

    @Test
    public void testExample() throws Exception {
        Client client = client()
                .withLastName("Doe")
                .withFirstName("John")
                .build();
        this.entityManager.persist(client);

        Client expected = this.repository.findAll().iterator().next();
        assertThat(expected.getLastName()).isEqualTo("Doe");
        assertThat(expected.getLastName()).isEqualTo("Doe");
    }

}