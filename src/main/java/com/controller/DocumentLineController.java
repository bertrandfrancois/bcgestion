package com.controller;

import com.beans.Client;
import com.beans.Document;
import com.beans.DocumentLine;
import com.service.ClientService;
import com.service.DocumentLineService;
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
@RequestMapping("/clients/{clientId}/")
public class DocumentLineController {

    private final ClientService clientService;

    private final ProjectService projectService;

    private final DocumentService documentService;

    private final DocumentLineService documentLineService;

    @Autowired
    public DocumentLineController(ClientService clientService,
                                  ProjectService projectService,
                                  DocumentService documentService,
                                  DocumentLineService documentLineService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.documentService = documentService;
        this.documentLineService = documentLineService;
    }

    @PostMapping("estimates/{documentId}/addLine")
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
            return "estimate_detail";
        }

        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + document.getId();
    }

    @GetMapping("estimates/{documentId}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@PathVariable("clientId") long clientId,
                                           @PathVariable("documentId") long documentId,
                                           @PathVariable("documentLineId") long documentLineId,
                                           Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);
        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        String mode = "EDIT";
            model.addAttribute("document", document);
            model.addAttribute("client", client);
            model.addAttribute("documentLine", documentLine);
            model.addAttribute("mode", mode);
            return "estimate_detail";
    }

    @PostMapping("estimates/{documentId}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                          BindingResult bindingResult,
                                          @PathVariable("clientId") long clientId,
                                          @PathVariable("documentId") long documentId,
                                          Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("client", client);
            return "estimate_detail";
        }
        documentLineService.save(documentLine);
        return "redirect:/clients/" + clientId + "/estimates/" + document.getId();
    }

    @GetMapping("estimates/{documentId}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        documentLineService.delete(documentLineId);
        return "redirect:/clients/" + clientId + "/estimates/" + documentId;
    }
}
