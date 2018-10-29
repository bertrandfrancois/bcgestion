package com.controller;

import com.factory.UrlFactory;
import com.model.Client;
import com.model.Document;
import com.model.DocumentLine;
import com.model.ServiceInvoice;
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
@RequestMapping("/clients/{clientId}/services")
public class ServiceInvoiceController {

    private ClientService clientService;

    private DocumentService documentService;

    private UrlFactory urlFactory;

    private DocumentLineService documentLineService;

    @Autowired
    public ServiceInvoiceController(ClientService clientService,
                                    DocumentService documentService,
                                    UrlFactory urlFactory,
                                    DocumentLineService documentLineService) {
        this.clientService = clientService;
        this.documentService = documentService;
        this.urlFactory = urlFactory;
        this.documentLineService = documentLineService;
    }

    @GetMapping("/{documentId}")
    public String showServiceInvoiceDocument(@PathVariable("clientId") long clientId,
                                             @PathVariable("documentId") long documentId,
                                             Model model) {
        Client client = clientService.find(clientId);
        ServiceInvoice document = (ServiceInvoice) documentService.find(documentId);
        DocumentLine documentLine = new DocumentLine();
        model.addAttribute("client", client);
        model.addAttribute("document", document);
        model.addAttribute("documentLine", documentLine);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newServiceInvoiceDocumentLine(clientId, documentId));

        return "service_invoice_detail";
    }

    @GetMapping("/create")
    public String createServiceInvoiceDocument(@PathVariable("clientId") long clientId,
                                               Model model) {
        Client client = clientService.find(clientId);
        ServiceInvoice serviceInvoice = new ServiceInvoice();
        model.addAttribute("client", client);
        model.addAttribute("serviceInvoice", serviceInvoice);
        model.addAttribute("mode", Mode.NEW);
        return "service_invoice_form";
    }

    @PostMapping("/create")
    public String saveServiceInvoiceDocument(@Valid @ModelAttribute ServiceInvoice document,
                                             BindingResult bindingResult,
                                             @PathVariable("clientId") long clientId,
                                             Model model) {
        Client client = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("mode", Mode.NEW);
            return "service_invoice_form";
        }
        document.setClient(client);
        Document savedDocument = documentService.save(document);
        return "redirect:/clients/" + clientId + "/services/" + savedDocument.getId() + "?createSuccess";
    }

    @GetMapping("/{documentId}/edit")
    public String editServiceInvoice(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     Model model) {
        Client client = clientService.find(clientId);
        ServiceInvoice serviceInvoice = (ServiceInvoice) documentService.find(documentId);
        model.addAttribute("serviceInvoice", serviceInvoice);
        model.addAttribute("client", client);
        model.addAttribute("mode", Mode.EDIT);
        return "service_invoice_form";
    }

    @PostMapping("/{documentId}/edit")
    public String editServiceInvoice(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @Valid @ModelAttribute ServiceInvoice serviceInvoice,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            Client client = clientService.find(clientId);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("client", client);
            return "service_invoice_form";
        }
        documentService.save(serviceInvoice);
        return "redirect:/clients/" + clientId + "/services/" + documentId + "?editSuccess";
    }

    @PostMapping("/{documentId}/delete")
    public String deleteServiceInvoiceDocument(@PathVariable("clientId") long clientId,
                                               @PathVariable("documentId") long documentId) {
        documentService.delete(documentId);
        return "redirect:/clients/" + clientId;
    }

    @PostMapping("/{documentId}/addLine")
    public String addServiceInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
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
            model.addAttribute("url", urlFactory.newServiceInvoiceDocumentLine(clientId, documentId));
            return "service_invoice_detail";
        }

        document.addDocumentLine(documentLine);
        documentService.save(document);
        return "redirect:/clients/" + clientId + "/services/" + document.getId();
    }

    @GetMapping("/{documentId}/editLine/{documentLineId}")
    public String editServiceInvoiceDocumentLine(@PathVariable("clientId") long clientId,
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
        model.addAttribute("url", urlFactory.editServiceInvoiceDocumentLine(clientId, documentId, documentLineId));

        return "service_invoice_detail";
    }

    @PostMapping("/{documentId}/editLine/{documentLineId}")
    public String editServiceInvoiceDocumentLine(@Valid @ModelAttribute DocumentLine documentLine,
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
            model.addAttribute("url", urlFactory.editServiceInvoiceDocumentLine(clientId, documentId, documentLineId));
            return "service_invoice_detail";
        }
        documentLineService.save(documentLine);
        return "redirect:/clients/" + clientId + "/services/" + document.getId();
    }

    @GetMapping("/{documentId}/deleteLine/{documentLineId}")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("documentId") long documentId,
                                     @PathVariable("documentLineId") long documentLineId) {
        documentLineService.delete(documentLineId);
        return "redirect:/clients/" + clientId + "/services/" + documentId;
    }
}
