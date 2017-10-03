package com.service;

import com.beans.Client;
import com.repository.ClientRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static com.testbuilder.ClientTestBuilder.client;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientService.class)
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    private Client client, otherClient;

    @Before
    public void setUp() throws Exception {
        client = client().build();
        otherClient = client().build();
    }

    @Test
    public void findAll() throws Exception {
        given(clientRepository.findAll()).willReturn(Lists.newArrayList(client, otherClient));

        assertThat(clientService.findAll()).contains(client, otherClient);
    }

    @Test
    public void save() throws Exception {
        clientService.save(client);

        verify(clientRepository).save(client);
    }

    @Test
    public void find() throws Exception {
        given(clientRepository.findOne(1L)).willReturn(client);

        assertThat(clientService.find(1L)).isEqualTo(client);

    }

    @Test
    public void delete() throws Exception {
        clientService.delete(1L);

        verify(clientRepository).delete(1L);
    }

}
