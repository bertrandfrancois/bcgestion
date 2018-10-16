package com.controller;

import com.model.Client;
import com.model.Project;
import com.service.ClientService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/{clientId}")
public class ProjectController {

    private final ProjectService projectService;

    private final ClientService clientService;

    @Autowired
    public ProjectController(ProjectService projectService, ClientService clientService) {
        this.projectService = projectService;
        this.clientService = clientService;
    }

    @GetMapping("/projects/{projectId}")
    public String createProject(@PathVariable("clientId") long clientId, @PathVariable("projectId") long projectId, Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        return "project_detail";
    }

    @GetMapping("/projects/create")
    public String createProject(@PathVariable("clientId") long clientId, Model model) {
        Project project = new Project();
        Client client = clientService.find(clientId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        return "create_project";
    }

    @PostMapping("/projects/create")
    public String saveProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable("clientId") long clientId,
                              Model model) {
        Client client = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            return "create_project";
        }
        project.setClient(client);
        Project lastProject = projectService.save(project);
        return "redirect:/clients/" + clientId + "/projects/" + lastProject.getId();
    }

    @GetMapping("/projects/{projectId}/edit")
    public String editProject(@PathVariable("clientId") long clientId,
                              @PathVariable("projectId") long projectId,
                              Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        return "edit_project";
    }

    @PostMapping("/projects/{projectId}/edit")
    public String editProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable("clientId") long clientId,
                              Model model) {
        Client client  = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            return "edit_project";
        }
        projectService.save(project);
        return "redirect:/clients/" + clientId + "/projects/" + project.getId();
    }
}
