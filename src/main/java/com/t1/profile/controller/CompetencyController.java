package com.t1.profile.controller;

import com.t1.profile.model.Competency;
import com.t1.profile.service.CompetencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/competencies")
@PreAuthorize("hasRole('ADMIN')")
public class CompetencyController {

    private final CompetencyService competencyService;

    @Autowired
    public CompetencyController(CompetencyService competencyService) {
        this.competencyService = competencyService;
    }

    @GetMapping
    public String listCompetencies(Model model) {
        List<Competency> competencies = competencyService.findAll();
        model.addAttribute("competencies", competencies);
        return "competencies/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("competency", new Competency());
        model.addAttribute("competencies", competencyService.findAll());
        return "competencies/add";
    }


    @PostMapping("/add")
    public String addCompetency(@ModelAttribute Competency competency) {
        competencyService.save(competency);
        return "redirect:/admin/competencies";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Competency competency = competencyService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid competency Id:" + id));
        model.addAttribute("competency", competency);
        return "competencies/edit";
    }

    @PostMapping("/edit/{id}")
    public String editCompetency(@PathVariable Integer id, @ModelAttribute Competency competency) {
        competency.setId(id);
        competencyService.save(competency);
        return "redirect:/admin/competencies";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompetency(@PathVariable Integer id) {
        competencyService.deleteById(id);
        return "redirect:/admin/competencies";
    }
}
