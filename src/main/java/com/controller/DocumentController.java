package com.controller;

import com.beans.Client;
import com.beans.Document;
import com.beans.DocumentLine;
import com.beans.Estimate;
import com.beans.Invoice;
import com.beans.Project;
import com.service.ClientService;
import com.service.DocumentService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/{clientId}")
public class DocumentController {

    private final ClientService clientService;

    private final ProjectService projectService;

    private final DocumentService documentService;

    @Autowired
    public DocumentController(ClientService clientService, ProjectService projectService, DocumentService documentService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.documentService = documentService;
    }

    @GetMapping("/projects/{projectId}/documents/{documentId}")
    public String showProjectDocument(@PathVariable("clientId") long clientId,
                                      @PathVariable("projectId") long projectId,
                                      @PathVariable("documentId") long documentId,
                                      Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        Invoice document = (Invoice) documentService.find(documentId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("document", document);

        return "document_detail";
    }

    @GetMapping("/estimates/{documentId}")
    public String showEstimateDocument(@PathVariable("clientId") long clientId,
                                       @PathVariable("documentId") long documentId,
                                       Model model) {
        Client client = clientService.find(clientId);
        Estimate document = (Estimate) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("client", client);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);

        return "estimate_detail";
    }

    @GetMapping("/projects/{projectId}/documents/create")
    public String createProjectDocument(@PathVariable("clientId") long clientId,
                                        @PathVariable("projectId") long projectId,
                                        Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        Invoice invoice = new Invoice();
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("document", invoice);

        return "create_document";
    }

    @GetMapping("/estimates/create")
    public String createEstimateDocument(@PathVariable("clientId") long clientId,
                                         Model model) {
        Client client = clientService.find(clientId);
        Estimate estimate = new Estimate();
        model.addAttribute("client", client);
        model.addAttribute("estimate", estimate);

        return "create_estimate";
    }

    @PostMapping("/projects/{projectId}/documents/create")
    public String saveProjectDocument(@Valid @ModelAttribute Invoice document,
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

    @PostMapping("/estimates/create")
    public String saveEstimateDocument(@Valid @ModelAttribute Estimate document,
                                       BindingResult bindingResult,
                                       @PathVariable("clientId") long clientId,
                                       Model model) {
        Client client = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            return "create_estimate";
        }
        document.setClient(client);
        Document savedDocument = documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + savedDocument.getId() + "?createSuccess";
    }

    @GetMapping("/estimates/{documentId}/edit")
    public String editEstimate(@PathVariable("clientId") long clientId,
                               @PathVariable("documentId") long documentId,
                               Model model) {
        Client client = clientService.find(clientId);
        Estimate estimate = (Estimate) documentService.find(documentId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("client", client);
        return "edit_estimate";
    }

    @PostMapping("/estimates/{documentId}/edit")
    public String editEstimate(@PathVariable("clientId") long clientId,
                               @PathVariable("documentId") long documentId,
                               @Valid @ModelAttribute Estimate estimate,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            Client client = clientService.find(clientId);
            model.addAttribute("client", client);
            return "edit_estimate";
        }
        documentService.save(estimate);
        return "redirect:/clients/" + clientId + "/estimates/" + documentId + "?editSuccess";
    }

    @PostMapping("/estimates/{documentId}/delete")
    public String deleteEstimateDocument(@PathVariable("clientId") long clientId,
                                         @PathVariable("documentId") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId;
    }
}
