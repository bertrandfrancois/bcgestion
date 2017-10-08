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
    public void setup(){
        client = client()
                .build();

        otherClient = client()
                .build();

        this.entityManager.persist(client);
        this.entityManager.persist(otherClient);

    }

    @Test
    public void findAll() throws Exception {
        assertThat(repository.findAll()).containsExactly(client, otherClient);
    }

    @Test
    public void findOne() throws Exception {
        assertThat(repository.findOne(client.getId())).isEqualTo(client);
    }

    @Test
    public void delete() throws Exception {
        repository.delete(client.getId());

        assertThat(repository.findAll()).containsOnly(otherClient);
    }

    @Test
    public void findTopByOrderByIdDesc() throws Exception {
        assertThat(repository.findTopByOrderByIdDesc()).isEqualTo(otherClient);
    }
}