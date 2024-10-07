package com.zett.springbootmvc.controllers;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.zett.springbootmvc.dtos.product.ProductCreateDTO;
import com.zett.springbootmvc.dtos.product.ProductDTO;
import com.zett.springbootmvc.services.CategoryService;
import com.zett.springbootmvc.services.ProductService;

import jakarta.validation.Valid;

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
            @RequestParam(name = "categoryName", required = false) String categoryName,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        var pageable = PageRequest.of(page, size);
        var products = productService.search(keyword, categoryName, pageable);
        model.addAttribute("products", products);

        model.addAttribute("keyword", keyword);

        model.addAttribute("page", page);

        model.addAttribute("pageSize", size);

        model.addAttribute("totalPages", products.getTotalPages());

        model.addAttribute("totalElements", products.getTotalElements());

        model.addAttribute("pageLimit", 2);
        
        model.addAttribute("pageSizes", new Integer[] { 2, 5, 10, 20, 50, 100 });

        var categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        model.addAttribute("categoryName", categoryName);
        return "products/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        var productCreateDTO = new ProductCreateDTO();
        model.addAttribute("productCreateDTO", productCreateDTO);
        var categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "products/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute @Valid ProductCreateDTO productCreateDTO,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            if (productCreateDTO.getCategoryId() == null) {
                bindingResult.rejectValue("categoryId", "category", "Category is required");
            }
            var categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            return "products/create";
        }

        productService.create(productCreateDTO);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model) {
        var productDTO = productService.findById(id);
        model.addAttribute("productDTO", productDTO);

        var categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, @ModelAttribute ProductDTO productDTO) {
        productService.update(id, productDTO);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
