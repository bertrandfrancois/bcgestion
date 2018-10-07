package com.controller;

import com.beans.Client;
import com.beans.Document;
import com.beans.DocumentLine;
import com.service.ClientService;
import com.service.DocumentService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/{clientId}/")
public class DocumentLineController {

    private final ClientService clientService;

    private final ProjectService projectService;

    private final DocumentService documentService;

    @Autowired
    public DocumentLineController(ClientService clientService, ProjectService projectService, DocumentService documentService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.documentService = documentService;
    }

    @PostMapping("estimates/documents/{documentId}/addLine")
    public String addEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                          BindingResult bindingResult,
                                          @PathVariable("clientId") long clientId,
                                          @PathVariable("documentId") long documentId,
                                          Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("client", client);
            return "estimate_document_detail";
        }
        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/documents/" + document.getId();
    }
}
