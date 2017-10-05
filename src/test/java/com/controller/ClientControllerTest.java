package com.controller;

import com.beans.Client;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static com.google.common.collect.Lists.newArrayList;
import static com.testbuilder.ClientTestBuilder.client;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class)
public class ClientControllerTest {

    @Autowired
    private WebClient webClient;

    @MockBean
    private ClientService clientService;

    private Client client, otherClient;

    @Before
    public void setUp() throws Exception {
        client = client()
                .withId(1L)
                .build();

        otherClient = client()
                .withId(2L)
                .withFirstName("Jane")
                .build();
    }

    @Test
    public void clients() throws Exception {
        given(clientService.findAll()).willReturn(newArrayList(client, otherClient));

        HtmlPage page = webClient.getPage("/clients");
        HtmlTable table = page.getHtmlElementById("dataTable");

        assertThat(table.getRowCount()).isEqualTo(3);
        assertThat(table.getCellAt(1, 0).asText()).isEqualTo(client.getLastName());
        assertThat(table.getCellAt(1, 1).asText()).isEqualTo(client.getFirstName());
        assertThat(table.getCellAt(1, 2).asText()).isEqualTo(client.getAddress().getStreet());
        assertThat(table.getCellAt(1, 4).asText()).isEqualTo(client.getAddress().getCity());
        assertThat(table.getCellAt(1, 3).asText()).isEqualTo(client.getAddress().getPostCode());
    }

    @Test
    public void createClient() throws Exception {
        HtmlPage page = webClient.getPage("/clients/create");
        HtmlForm form = page.getFormByName("createClient");
        form.getInputByName("lastName").setValueAttribute("Doe");
        form.getInputByName("firstName").setValueAttribute("John");
        form.getInputByName("address.street").setValueAttribute("street");
        form.getInputByName("address.postCode").setValueAttribute("12345");
        form.getInputByName("address.city").setValueAttribute("city");
        form.getInputByName("mail").setValueAttribute("mail@mail.com");
        form.getInputByName("phoneNumber").setValueAttribute("phoneNumber");
        form.getInputByName("taxNumber").setValueAttribute("taxNumber");

        HtmlSubmitInput button = form.getInputByName("Valider");

        button.click();

        verify(clientService).save(Mockito.any(Client.class));
    }

    @Test
    public void createClient_withValidationErrors_invalidMail() throws Exception {
        HtmlPage page = webClient.getPage("/clients/create");
        HtmlForm form = page.getFormByName("createClient");
        form.getInputByName("lastName").setValueAttribute("Doe");
        form.getInputByName("firstName").setValueAttribute("John");
        form.getInputByName("address.street").setValueAttribute("street");
        form.getInputByName("address.postCode").setValueAttribute("12345");
        form.getInputByName("address.city").setValueAttribute("city");
        form.getInputByName("mail").setValueAttribute("invalidMail");
        form.getInputByName("phoneNumber").setValueAttribute("phoneNumber");
        form.getInputByName("taxNumber").setValueAttribute("taxNumber");

        HtmlSubmitInput button = form.getInputByName("Valider");

        button.click();

        verify(clientService, never()).save(Mockito.any(Client.class));
    }

    @Test
    public void showClient() throws Exception {
        given(clientService.find(1)).willReturn(client);
        HtmlPage page = webClient.getPage("/clients/1");
        HtmlTable table = page.getHtmlElementById("clientDetail");

        assertThat(table.getCellAt(0, 1).asText()).isEqualTo(client.getLastName());
        assertThat(table.getCellAt(1, 1).asText()).isEqualTo(client.getFirstName());
        assertThat(table.getCellAt(2, 1).asText()).isEqualTo(client.getAddress().getStreet());
        assertThat(table.getCellAt(3, 1).asText()).isEqualTo(client.getAddress().getPostCode());
        assertThat(table.getCellAt(4, 1).asText()).isEqualTo(client.getAddress().getCity());
        assertThat(table.getCellAt(5, 1).asText()).isEqualTo(client.getMail());
        assertThat(table.getCellAt(6, 1).asText()).isEqualTo(client.getPhoneNumber());
        assertThat(table.getCellAt(7, 1).asText()).isEqualTo(client.getTaxNumber());
    }

    @Test
    public void editClient() throws Exception {
        given(clientService.find(1)).willReturn(client);

        HtmlPage page = webClient.getPage("/clients/1/edit");
        HtmlForm form = page.getFormByName("editClient");
        form.getInputByName("firstName").setValueAttribute("Jane");
        HtmlSubmitInput button = form.getInputByName("Valider");

        button.click();

        verify(clientService).save(Mockito.any(Client.class));
    }

    @Test
    public void editClient_withValidationErrors_invalidLastName() throws Exception {
        given(clientService.find(1)).willReturn(client);

        HtmlPage page = webClient.getPage("/clients/1/edit");
        HtmlForm form = page.getFormByName("editClient");
        form.getInputByName("firstName").setValueAttribute("");
        HtmlSubmitInput button = form.getInputByName("Valider");

        button.click();

        verify(clientService, never()).save(Mockito.any(Client.class));
    }

    @Test
    public void deleteClient() throws Exception {
    }
}