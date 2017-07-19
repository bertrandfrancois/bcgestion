package com.controller;

import com.beans.Client;
import com.beans.Project;
import com.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/clients")
    public String clients(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("clients", clientService.findAll());
        return "clients";
    }
    @GetMapping("/newClient")
    public String newClient(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("clients", clientService.findAll());
        return "new_client";
    }

    @PostMapping("/newClient")
    public String addCustomer(@Valid @ModelAttribute Client client, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "new_client";
        }
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}")
    public String showClient(@PathVariable long id, Model model) {
        Client client = clientService.find(id);
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("client", client);
        return "showClient";
    }

    @GetMapping("/clients/{id}/edit")
    public String editClient(@PathVariable long id, Model model) {
        Client client = clientService.find(id);
        model.addAttribute("client", client);
        return "editClient";
    }

    @PostMapping("/clients/{id}/edit")
    public String editClient(@ModelAttribute Client client) {
        clientService.save(client);
        return "redirect:/clients";
    }

    @GetMapping("/clients/{id}/delete")
    public String deleteClient(@PathVariable long id) {
        clientService.delete(id);
        return "redirect:/clients";
    }
}
