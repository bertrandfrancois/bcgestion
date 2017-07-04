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
@RequestMapping("clients/{id}")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/projects")
    public String clientProjects(@PathVariable long id, Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        return "projects";
    }

    @PostMapping("/projects")
    public String add(@Valid @ModelAttribute Project project,
                      @PathVariable long id,
                      BindingResult bindingResult,
                      Model model) {

        if (bindingResult.hasErrors()) {
            return "projects";
        }
        Client client = clientService.find(id);
        project.setClient(client);
        projectService.save(project);
        return "redirect:/clients";
    }
}