package com.zett.springbootmvc.controllers;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.zett.springbootmvc.dtos.product.ProductCreateDTO;
import com.zett.springbootmvc.dtos.product.ProductDTO;
import com.zett.springbootmvc.services.CategoryService;
import com.zett.springbootmvc.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        var pageable = PageRequest.of(page, pageSize);
        var products = productService.search(keyword,pageable);
        model.addAttribute("keyword", keyword);
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        var categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "products/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ProductCreateDTO productCreateDTO){
        productService.create(productCreateDTO);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model){
        var productDTO = productService.findById(id);
        model.addAttribute("productDTO", productDTO);

        var categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, @ModelAttribute ProductDTO productDTO){
        productService.update(id, productDTO);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id){
        productService.delete(id);
        return "redirect:/products";
    }
}
