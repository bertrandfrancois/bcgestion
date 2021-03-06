package com.controller;

import com.factory.UrlFactory;
import com.model.Client;
import com.model.Document;
import com.model.DocumentLine;
import com.model.Estimate;
import com.model.Mode;
import com.service.ClientService;
import com.service.DocumentLineService;
import com.service.DocumentService;
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
@RequestMapping("/clients/{clientId}/estimates")
public class EstimateController {

    private ClientService clientService;

    private DocumentService documentService;

    private UrlFactory urlFactory;

    private DocumentLineService documentLineService;

    @Autowired
    public EstimateController(ClientService clientService,
                              DocumentService documentService,
                              UrlFactory urlFactory,
                              DocumentLineService documentLineService) {
        this.clientService = clientService;
        this.documentService = documentService;
        this.urlFactory = urlFactory;
        this.documentLineService = documentLineService;
    }

    @GetMapping("/{documentId}")
    public String showEstimateDocument(@PathVariable("clientId") long clientId,
                                       @PathVariable("documentId") long documentId,
                                       Model model) {
        Client client = clientService.find(clientId);
        Estimate document = (Estimate) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("client", client);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newEstimateDocumentLine(clientId, documentId));

        return "estimate_detail";
    }

    @GetMapping("/create")
    public String createEstimateDocument(@PathVariable("clientId") long clientId,
                                         Model model) {
        Client client = clientService.find(clientId);
        Estimate estimate = new Estimate();
        model.addAttribute("client", client);
        model.addAttribute("estimate", estimate);
        model.addAttribute("mode", Mode.NEW);
        return "estimate_form";
    }

    @PostMapping("/create")
    public String saveEstimateDocument(@Valid @ModelAttribute Estimate document,
                                       BindingResult bindingResult,
                                       @PathVariable("clientId") long clientId,
                                       Model model) {
        Client client = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("mode", Mode.NEW);
            return "estimate_form";
        }
        document.setClient(client);
        Document savedDocument = documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + savedDocument.getId() + "?createSuccess";
    }

    @GetMapping("/{documentId}/edit")
    public String editEstimate(@PathVariable("clientId") long clientId,
                               @PathVariable("documentId") long documentId,
                               Model model) {
        Client client = clientService.find(clientId);
        Estimate estimate = (Estimate) documentService.find(documentId);
        model.addAttribute("estimate", estimate);
        model.addAttribute("client", client);
        model.addAttribute("mode", Mode.EDIT);
        return "estimate_form";
    }

    @PostMapping("/{documentId}/edit")
    public String editEstimate(@PathVariable("clientId") long clientId,
                               @PathVariable("documentId") long documentId,
                               @Valid @ModelAttribute Estimate estimate,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            Client client = clientService.find(clientId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("client", client);
            return "estimate_form";
        }
        documentService.save(estimate);
        return "redirect:/clients/" + clientId + "/estimates/" + documentId + "?editSuccess";
    }

    @PostMapping("/{documentId}/delete")
    public String deleteEstimateDocument(@PathVariable("clientId") long clientId,
                                         @PathVariable("documentId") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{documentId}/addLine")
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
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newEstimateDocumentLine(clientId, documentId));
            return "estimate_detail";
        }

        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/estimates/" + document.getId();
    }

    @GetMapping("/{documentId}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@PathVariable("clientId") long clientId,
                                           @PathVariable("documentId") long documentId,
                                           @PathVariable("documentLineId") long documentLineId,
                                           Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);
        DocumentLine documentLine = document.getDocumentLines().stream().filter(dl -> dl.getId().equals(documentLineId)).findFirst().get();
        model.addAttribute("document", document);
        model.addAttribute("client", client);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editEstimateDocumentLine(clientId, documentId, documentLineId));

        return "estimate_detail";
    }

    @PostMapping("/{documentId}/editLine/{documentLineId}")
    public String editEstimateDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
                                           BindingResult bindingResult,
                                           @PathVariable("clientId") long clientId,
                                           @PathVariable("documentId") long documentId,
                                           @PathVariable("documentLineId") long documentLineId,
                                           Model model) {
        Document document = documentService.find(documentId);
        Client client = clientService.find(clientId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("document", document);
            model.addAttribute("client", client);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editEstimateDocumentLine(clientId, documentId, documentLineId));
            return "estimate_detail";
        }
        documentLineService.save(documentLine);
        return "redirect:/clients/" + clientId + "/estimates/" + document.getId();
    }

    @GetMapping("/{documentId}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        documentLineService.delete(documentLineId);
        return "redirect:/clients/" + clientId + "/estimates/" + documentId;
    }
}
