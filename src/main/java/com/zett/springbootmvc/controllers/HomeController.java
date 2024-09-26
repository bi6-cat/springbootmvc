package com.zett.springbootmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String index(
        @RequestParam(name = "name", required = false, defaultValue = "World") String name, 
        @RequestParam(name = "age", required = false, defaultValue = "0") int age, 
        Model model) {
        model.addAttribute("message", "Hello Spring Boot MVC"+ name + " " + age);
        return "home/index";
    }

    @GetMapping("/about")
    public ModelAndView about(ModelAndView modelAndView) {
        modelAndView.setViewName("home/about");
        modelAndView.addObject("message", "This is about page");
        return modelAndView;
    }

    @GetMapping("/contact")
    public String contact(ModelMap modelMap) {
        modelMap.addAttribute("message", "This is contact page");
        return "home/contact";
    }
}
