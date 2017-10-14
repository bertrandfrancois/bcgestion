package com.controller;

import com.beans.Client;
import com.beans.Document;
import com.beans.Invoice;
import com.beans.Project;
import com.service.ClientService;
import com.service.DocumentService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/{clientId}/projects/{projectId}/documents")
public class DocumentController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DocumentService documentService;

    @GetMapping("/{documentId}")
    public String showProject(@PathVariable("clientId") long clientId,
                                @PathVariable("projectId") long projectId,
                                @PathVariable("documentId") long documentId,
                                Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        Invoice document= (Invoice) documentService.find(documentId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("document", document);

        return "document_detail";
    }

    @GetMapping("/create")
    public String createProject(@PathVariable("clientId") long clientId,
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

    @PostMapping("/create")
    public String saveProject(@Valid @ModelAttribute Invoice document,
                              BindingResult bindingResult,
                              @PathVariable ("clientId") long clientId,
                              @PathVariable ("projectId") long projectId,
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
//        Project lastProject = projectService.findLastProject();
        return "redirect:/clients/" + clientId + "/projects/" +project.getId();
    }
}
