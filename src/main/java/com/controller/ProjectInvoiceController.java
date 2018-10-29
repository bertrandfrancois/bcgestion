package com.controller;

import com.factory.UrlFactory;
import com.model.Client;
import com.model.ProjectInvoice;
import com.model.Project;
import com.service.ClientService;
import com.service.DocumentService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

public class ProjectInvoiceController {


    private final ClientService clientService;

    private final ProjectService projectService;

    private final DocumentService documentService;

    private final UrlFactory urlFactory;

    @Autowired
    public ProjectInvoiceController(ClientService clientService,
                              ProjectService projectService,
                              DocumentService documentService,
                              UrlFactory urlFactory) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.documentService = documentService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/projects/{projectId}/documents/{documentId}")
    public String showProjectDocument(@PathVariable("clientId") long clientId,
                                      @PathVariable("projectId") long projectId,
                                      @PathVariable("documentId") long documentId,
                                      Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        ProjectInvoice document = (ProjectInvoice) documentService.find(documentId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("document", document);

        return "document_detail";
    }

    @GetMapping("/projects/{projectId}/documents/create")
    public String createProjectDocument(@PathVariable("clientId") long clientId,
                                        @PathVariable("projectId") long projectId,
                                        Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        ProjectInvoice projectInvoice = new ProjectInvoice();
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("document", projectInvoice);

        return "create_document";
    }

    @PostMapping("/projects/{projectId}/documents/create")
    public String saveProjectDocument(@Valid @ModelAttribute ProjectInvoice document,
                                      BindingResult bindingResult,
                                      @PathVariable("clientId") long clientId,
                                      @PathVariable("projectId") long projectId,
                                      Model model) {
        Client client = clientService.find(clientId);
        Project project = projectService.find(projectId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("project", project);
            return "create_document";
        }
        document.setClient(client);
        document.setProject(project);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/projects/" + project.getId();
    }



}
