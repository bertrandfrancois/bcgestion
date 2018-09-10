package com.controller;

import com.beans.Client;
import com.beans.Project;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.service.ClientService;
import com.service.ProjectService;
import com.testbuilder.DateTestBuilder;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;

import static com.testbuilder.ClientTestBuilder.client;
import static com.testbuilder.ProjectTestBuilder.project;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private WebClient webClient;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private ClientService clientService;

    @Captor
    private ArgumentCaptor<Project> capturedProject;

    private Client client;

    private Project createdProject;

    @Before
    public void setUp() throws ParseException {
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client = client().withId(1L).build();
        createdProject = project().withClient(client).withId(5L).build();
        given(clientService.find(1L)).willReturn(client);
        given(projectService.find(5L)).willReturn(createdProject);
    }

    @Test
    public void createProject() throws Exception {
        given(projectService.save(capturedProject.capture())).willReturn(createdProject);

        HtmlPage page = webClient.getPage("/clients/1/projects/create");
        HtmlForm form = page.getFormByName("createProject");
        form.getInputByName("description").setValueAttribute("description");
        form.getInputByName("address.street").setValueAttribute("street");
        form.getInputByName("address.postCode").setValueAttribute("12345");
        form.getInputByName("address.city").setValueAttribute("city");
        form.getInputByName("startDate").setValueAttribute("2017-01-01");
        form.getInputByName("endDate").setValueAttribute("2017-12-31");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");
        Page detailPage = button.click();

        assertThat(capturedProject.getValue()).isEqualToIgnoringGivenFields(createdProject, "id", "address");
        assertThat(capturedProject.getValue().getAddress()).isEqualToComparingFieldByField(createdProject.getAddress());
        assertThat(detailPage.getUrl().getPath()).isEqualTo("/clients/1/projects/5");
    }

    @Test
    public void showProject() throws Exception {
        HtmlPage page = webClient.getPage("/clients/1/projects/5");

        HtmlTable table = page.getHtmlElementById("projectDetail");

        Assertions.assertThat(table.getCellAt(0, 1).asText()).isEqualTo(createdProject.getDescription());
        Assertions.assertThat(table.getCellAt(1, 1).asText()).isEqualTo(createdProject.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Assertions.assertThat(table.getCellAt(2, 1).asText()).isEqualTo(createdProject.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Assertions.assertThat(table.getCellAt(3, 1).asText()).isEqualTo(createdProject.getAddress().getStreet());
        Assertions.assertThat(table.getCellAt(4, 1).asText()).isEqualTo(createdProject.getAddress().getPostCode());
        Assertions.assertThat(table.getCellAt(5, 1).asText()).isEqualTo(createdProject.getAddress().getCity());
    }

    @Test
    public void editProject() throws Exception {
        HtmlPage page = webClient.getPage("/clients/1/projects/5/edit");
        HtmlForm form = page.getFormByName("editProject");
        form.getInputByName("description").setValueAttribute("editedDescription");
        HtmlButton button = form.getOneHtmlElementByAttribute("button", "type",
                                                              "submit");

        Page detailPage = button.click();

        verify(projectService).save(capturedProject.capture());
        assertThat(capturedProject.getValue().getDescription()).isEqualTo("editedDescription");
        assertThat(detailPage.getUrl().getPath()).isEqualTo("/clients/1/projects/5");
    }
}