package com.controller;

import com.beans.Client;
import com.service.ClientService;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.testbuilder.ClientTestBuilder.client;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class)
public class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ClientService clientService;

    private Client client, otherClient;

    @Before
    public void setUp() throws Exception {
        client = client().build();
        otherClient = client()
                .withFirstName("Bat")
                .withLastName("man")
                .build();
    }

    @Test
    public void clients() throws Exception {

        given(clientService.findAll()).willReturn(Lists.newArrayList(client, otherClient));

        mvc.perform(get("/clients")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John")))
                .andExpect(content().string(containsString("Doe")))
                .andExpect(content().string(containsString("Bat")))
                .andExpect(content().string(containsString("man")));
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