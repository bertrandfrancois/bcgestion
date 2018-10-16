package com.service;

import com.model.Client;
import com.config.MockitoTest;
import com.repository.ClientRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.testbuilder.ClientTestBuilder.client;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class ClientServiceTest extends MockitoTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private Client client, otherClient;

    @Before
    public void setUp() {
        client = client().build();
        otherClient = client().build();
    }

    @Test
    public void findAll() {
        given(clientRepository.findAll()).willReturn(Lists.newArrayList(client, otherClient));

        assertThat(clientService.findAll()).contains(client, otherClient);
    }

    @Test
    public void save() {
        clientService.save(client);

        verify(clientRepository).save(client);
    }

    @Test
    public void find() {
        given(clientRepository.findById(1L)).willReturn(Optional.of(client));

        assertThat(clientService.find(1L)).isEqualTo(client);
    }

    @Test
    public void delete() {
        clientService.delete(1L);

        verify(clientRepository).deleteById(1L);
    }
}