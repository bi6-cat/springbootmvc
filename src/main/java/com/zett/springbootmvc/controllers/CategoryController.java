package com.zett.springbootmvc.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zett.springbootmvc.dtos.category.CategoryDTO;
import com.zett.springbootmvc.services.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model) {
        var categoriesDTO = categoryService.findAll();
        model.addAttribute("categoriesDTO", categoriesDTO);
        return "categories/index";
    }

    @GetMapping("/create")
    public String create() {
        return "categories/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute CategoryDTO categoryDTO){
        categoryService.create(categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, ModelMap model){
        var categoryDTO = categoryService.findById(id);
        model.addAttribute("categoryDTO", categoryDTO);
        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, @ModelAttribute CategoryDTO categoryDTO){
        categoryService.update(id, categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id){
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
