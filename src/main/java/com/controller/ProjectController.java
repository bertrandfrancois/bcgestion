package com.controller;

import com.beans.Project;
import com.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projectService.findAll());
        return "projects";
    }

    @PostMapping("/projects/add")
    public String add(@Valid @ModelAttribute Project project, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "projects";
        }
        projectService.save(project);
        return "redirect:/projects";
    }
}