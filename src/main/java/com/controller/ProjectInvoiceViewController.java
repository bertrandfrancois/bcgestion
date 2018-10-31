package com.controller;

import com.model.ProjectInvoice;
import com.google.common.collect.Maps;
import com.pdf.InvoicePdfView;
import com.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class ProjectInvoiceViewController {

    private final DocumentService documentService;

    @Autowired
    public ProjectInvoiceViewController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/display/projectInvoice/{documentId}")
    public ModelAndView document(@PathVariable("documentId") long id, ModelAndView model) {
        ProjectInvoice document = (ProjectInvoice) documentService.find(id);
        HashMap<String, Object> parameters = Maps.newHashMap();
        parameters.put("document", document);

        return new ModelAndView(new InvoicePdfView(), parameters);
    }

}
