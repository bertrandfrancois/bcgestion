package com.controller;

import com.beans.Client;
import com.beans.Project;
import com.service.ClientService;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/clients/{id}")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/projects/create")
    public String createProject(@PathVariable long id, Model model) {
        Project project = new Project();
        Client client = clientService.find(id);
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        return "create_project";
    }

    @PostMapping("/projects/create")
    public String saveProject(@Valid @ModelAttribute Project project,
                              BindingResult bindingResult,
                              @PathVariable long id,
                              Model model) {
        Client client = clientService.find(id);
        model.addAttribute("client", client);
        if (bindingResult.hasErrors()) {
            return "create_project";
        }
        project.setClient(client);
        projectService.save(project);
        return "redirect:/clients/" + id;
    }
}