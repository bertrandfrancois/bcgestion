package com.controller;

import com.beans.Client;
import com.config.WebSecurityConfig;
import com.repository.UserRepository;
import com.service.ClientService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.testbuilder.ClientTestBuilder.client;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
public class ClientControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void clients() throws Exception {
        Client client = client()
                .build();

        when(clientService.findAll()).thenReturn(Lists.newArrayList(client));

        mvc.perform(get("/clients")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")))
                .andExpect(content().string(containsString("Doe")));
    }

    @Test
    public void showCreateClient() throws Exception {
    }

    @Test
    public void createClient() throws Exception {
    }

    @Test
    public void showClient() throws Exception {
    }

    @Test
    public void editClient() throws Exception {
    }

    @Test
    public void editClient1() throws Exception {
    }

    @Test
    public void deleteClient() throws Exception {
    }

}