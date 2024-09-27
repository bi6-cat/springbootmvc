package com.zett.springbootmvc.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zett.springbootmvc.dtos.category.CategoryDTO;
import com.zett.springbootmvc.entities.Category;
import com.zett.springbootmvc.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<CategoryDTO> findAll() {
        var categories = categoryRepository.findAll();

        var categoriesDTO = categories.stream().map(category -> {
            var categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setDescription(category.getDescription());
            return categoryDTO;
        }).toList();

        return categoriesDTO;
    }

    @Override
    public CategoryDTO findById(UUID id) {
        var category = categoryRepository.findById(id).orElse(null);

        if(category == null){
            return null;
        }

        var categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        
        return categoryDTO;
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        if(categoryDTO == null){
            throw new IllegalArgumentException("Required categoryDTO");
        }

        var existingCategory = categoryRepository.findByName(categoryDTO.getName());
        if(existingCategory != null){
            throw new IllegalArgumentException("Category already exists");
        }

        var category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        category = categoryRepository.save(category);

        var newCategoryDTO = new CategoryDTO();
        newCategoryDTO.setId(category.getId());
        newCategoryDTO.setName(category.getName());
        newCategoryDTO.setDescription(category.getDescription());

        return newCategoryDTO;
    }

    @Override
    public CategoryDTO update(UUID id, CategoryDTO categoryDTO) {
        if(categoryDTO == null){
            throw new IllegalArgumentException("Required categoryDTO");
        }

        var category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new IllegalArgumentException("Category not found");
        }

        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());

        category = categoryRepository.save(category);

        var updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setId(category.getId());
        updatedCategoryDTO.setName(category.getName());
        updatedCategoryDTO.setDescription(category.getDescription());

        return updatedCategoryDTO;
    }

    @Override
    public void delete(String id) {
        var category = categoryRepository.findById(UUID.fromString(id)).orElse(null);
        if(category == null){
            throw new IllegalArgumentException("Category not found");
        }

        categoryRepository.delete(category);
    }

}
