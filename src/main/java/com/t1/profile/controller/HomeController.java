package com.t1.profile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Проверяем роль пользователя и перенаправляем на соответствующую страницу
        return "redirect:/admin/competencies";
    }
}
