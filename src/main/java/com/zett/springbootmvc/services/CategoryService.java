package com.zett.springbootmvc.services;

import java.util.List;
import java.util.UUID;

import com.zett.springbootmvc.dtos.category.CategoryDTO;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findById(UUID id);

    CategoryDTO create(CategoryDTO categoryDTO);
    
    CategoryDTO update(UUID id, CategoryDTO categoryDTO);
    
    void delete(String id);
}
