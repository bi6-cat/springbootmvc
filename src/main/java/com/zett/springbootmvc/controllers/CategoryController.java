package com.zett.springbootmvc.controllers;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
    public String index(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size,
            @RequestParam(required = false) String keyword,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        var categories = categoryService.findAll(keyword, pageable);
        model.addAttribute("categories", categories);

        model.addAttribute("keyword", keyword);
        //Passing totalPage to view
        model.addAttribute("totalPages", categories.getTotalPages());

        model.addAttribute("totalElements", categories.getTotalElements());

        model.addAttribute("page", page);

        model.addAttribute("pageSize", size);

        model.addAttribute("pageSizes", new Integer[]{5, 10, 20, 50, 100});
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
