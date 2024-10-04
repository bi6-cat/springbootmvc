package com.zett.springbootmvc.controllers;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.zett.springbootmvc.dtos.category.CategoryCreateDTO;
import com.zett.springbootmvc.dtos.category.CategoryDTO;
import com.zett.springbootmvc.services.CategoryService;

import jakarta.validation.Valid;


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
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false) String keyword,
            Model model) {
        
        Pageable pageable = null;
        if(order.equals("asc")){
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        var categories = categoryService.findAll(keyword, pageable);
        model.addAttribute("categories", categories);

        model.addAttribute("keyword", keyword);

        model.addAttribute("sortBy", sortBy);

        model.addAttribute("order", order);
        //Passing totalPage to view
        model.addAttribute("totalPages", categories.getTotalPages());

        model.addAttribute("totalElements", categories.getTotalElements());

        model.addAttribute("page", page);

        model.addAttribute("pageSize", size);

        model.addAttribute("pageSizes", new Integer[]{5, 10, 20, 50, 100});
        return "categories/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        var categoryCreateDTO = new CategoryCreateDTO();
        model.addAttribute("categoryCreateDTO", categoryCreateDTO);
        return "categories/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid CategoryCreateDTO categoryCreateDTO,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "categories/create";
        }

        categoryService.create(categoryCreateDTO);
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
