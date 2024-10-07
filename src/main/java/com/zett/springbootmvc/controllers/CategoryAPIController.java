package com.zett.springbootmvc.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zett.springbootmvc.dtos.category.CategoryCreateDTO;
import com.zett.springbootmvc.dtos.category.CategoryDTO;
import com.zett.springbootmvc.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPIController {
     private final CategoryService categoryService;

    public CategoryAPIController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // /api/categories with GET method => get all
    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<CategoryDTO>> getAll(){
        var categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    // api/categories/{id} with GET method => get by id
    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public ResponseEntity<CategoryDTO> getById(@PathVariable UUID id){
        var category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    // api/categories/{id} with DELETE method => delete by id
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create category")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryCreateDTO categoryCreateDTO){
        var category = categoryService.create(categoryCreateDTO);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update category by id")
    public ResponseEntity<CategoryDTO> create(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO){
        var category = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(category);
    }
}
