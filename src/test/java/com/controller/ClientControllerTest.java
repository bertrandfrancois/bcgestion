package com.controller;

import com.beans.Client;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.service.ClientService;
import com.testbuilder.ClientTestBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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

    @Captor
    private ArgumentCaptor<Client> createdClient, editedClient;

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
        given(clientService.findLastClient()).willReturn(client().withId(1L).build());
        given(clientService.find(1L)).willReturn(client().withId(1L).build());

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

        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                "submit");
        Page clientPage = button.click();

        assertThat(clientPage.getUrl().getPath()).isEqualTo("/clients/1");

        verify(clientService).save(createdClient.capture());
        assertThat(createdClient.getValue()).isEqualToComparingFieldByFieldRecursively(ClientTestBuilder.client().build());
        verify(clientService).findLastClient();
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

        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                "submit");

        button.click();

        verify(clientService, never()).save(Mockito.any(Client.class));
        verify(clientService, never()).findLastClient();
    }

    @Test
    public void showClient() throws Exception {
        given(clientService.find(1L)).willReturn(client);
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
        given(clientService.find(1L)).willReturn(client);

        HtmlPage page = webClient.getPage("/clients/1/edit");
        HtmlForm form = page.getFormByName("editClient");
        form.getInputByName("firstName").setValueAttribute("Jane");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                "submit");

        button.click();

        verify(clientService).save(editedClient.capture());
        assertThat(editedClient.getValue().getFirstName()).isEqualTo("Jane");
    }

    @Test
    public void editClient_withValidationErrors_invalidLastName() throws Exception {
        given(clientService.find(1)).willReturn(client);

        HtmlPage page = webClient.getPage("/clients/1/edit");
        HtmlForm form = page.getFormByName("editClient");
        form.getInputByName("firstName").setValueAttribute("");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                "submit");
        button.click();

        verify(clientService, never()).save(Mockito.any(Client.class));
    }

    @Test
    public void deleteClient() throws Exception {
        given(clientService.find(1L)).willReturn(client);
        HtmlPage page = webClient.getPage("/clients/1");
        HtmlForm form = page.getFormByName("deleteClient");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                "submit");
        Page clientsPage = button.click();
        assertThat(clientsPage.getUrl().getPath()).isEqualTo("/clients");
        verify(clientService).delete(1L);

    }
}
