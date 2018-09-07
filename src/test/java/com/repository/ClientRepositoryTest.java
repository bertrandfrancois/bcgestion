package com.repository;

import com.beans.Client;
import org.junit.Before;
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

    private Client client, otherClient;

    @Before
    public void setup() {
        client = client()
                .build();

        otherClient = client()
                .build();

        this.entityManager.persist(client);
        this.entityManager.persist(otherClient);
    }

    @Test
    public void findAll() {
        assertThat(repository.findAll()).containsExactly(client, otherClient);
    }

    @Test
    public void findOne() {
        assertThat(repository.findById(client.getId()).get()).isEqualTo(client);
    }

    @Test
    public void delete() {
        repository.deleteById(client.getId());

        assertThat(repository.findAll()).containsOnly(otherClient);
    }
}