package com.controller;

import com.factory.UrlFactory;
import com.model.Client;
import com.model.Mode;
import com.model.Project;
import com.service.ClientService;
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
@RequestMapping("/clients/{clientId}/projects")
public class ProjectController {

    private ProjectService projectService;

    private ClientService clientService;

    private UrlFactory urlFactory;

    @Autowired
    public ProjectController(ProjectService projectService,
                             ClientService clientService,
                             UrlFactory urlFactory) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.urlFactory = urlFactory;
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable("clientId") long clientId, @PathVariable("projectId") long projectId, Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);

        return "project_detail";
    }

    @GetMapping("/create")
    public String createProject(@PathVariable("clientId") long clientId, Model model) {
        Project project = new Project();
        Client client = clientService.find(clientId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("mode", Mode.NEW);
        model.addAttribute("url", urlFactory.newProject(clientId));

        return "project_form";
    }

    @PostMapping("/create")
    public String saveProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable("clientId") long clientId,
                              Model model) {
        Client client = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("mode", Mode.NEW);
            model.addAttribute("url", urlFactory.newProject(clientId));
            return "project_form";
        }
        project.setClient(client);
        Project savedProject = projectService.save(project);
        return "redirect:/clients/" + clientId + "/projects/" + savedProject.getId() + "?createSuccess";
    }

    @GetMapping("/{projectId}/edit")
    public String editProject(@PathVariable("clientId") long clientId,
                              @PathVariable("projectId") long projectId,
                              Model model) {
        Project project = projectService.find(projectId);
        Client client = clientService.find(clientId);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        model.addAttribute("mode", Mode.EDIT);
        model.addAttribute("url", urlFactory.editProject(clientId, projectId));
        return "project_form";
    }

    @PostMapping("/{projectId}/edit")
    public String editProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable("clientId") long clientId,
                              Model model) {
        Client client = clientService.find(clientId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("mode", Mode.EDIT);
            model.addAttribute("url", urlFactory.editProject(clientId, project.getId()));
            return "project_form";
        }
        projectService.save(project);
        return "redirect:/clients/" + clientId + "/projects/" + project.getId()+ "?editSuccess";
    }

    @PostMapping("/{projectId}/delete")
    public String deleteDocumentLine(@PathVariable("clientId") long clientId,
                                     @PathVariable("projectId") long projectId) {
        projectService.delete(projectId);
        return "redirect:/clients/" + clientId;
    }
}
