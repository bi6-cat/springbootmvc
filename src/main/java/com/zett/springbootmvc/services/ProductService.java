package com.zett.springbootmvc.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zett.springbootmvc.dtos.product.ProductCreateDTO;
import com.zett.springbootmvc.dtos.product.ProductDTO;

public interface ProductService {
    List<ProductDTO> findAll();

    List<ProductDTO> findAll(String keyword);

    Page<ProductDTO> findAll(String keyword, Pageable pageable);

    ProductDTO findById(UUID id);

    ProductDTO create(ProductCreateDTO productCreateDTO);

    ProductDTO update(UUID id, ProductDTO productDTO);

    void delete(UUID id);
}
