package com.service;

import com.beans.Client;
import com.repository.ClientRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Mock
    private Client client, otherClient;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ClientService clientService() {
            return new ClientService();
        }
    }

    @Before
    public void setUp() throws Exception {
        given(clientRepository.findAll()).willReturn(Lists.newArrayList(client, otherClient));
    }

    @Test
    public void findAll() throws Exception {
        assertThat(clientService.findAll()).contains(client, otherClient);
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void find() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

}