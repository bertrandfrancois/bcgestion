package com.controller;

import com.beans.Document;
import com.beans.Invoice;
import com.beans.Project;
import com.service.ClientService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("clients/{clientId}/project/{projectId}")
public class DocumentController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String projects(@PathVariable("clientId") long clientId, @PathVariable("projectId") long projectId, Model model) {
        Project project = projectService.find(projectId);
        model.addAttribute("project", project);
        model.addAttribute("document", new Invoice());
        return "documents";
    }
}
