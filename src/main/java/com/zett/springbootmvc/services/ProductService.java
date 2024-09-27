package com.zett.springbootmvc.services;

import java.util.List;
import java.util.UUID;

import com.zett.springbootmvc.dtos.product.ProductDTO;

public interface ProductService {
    List<ProductDTO> findAll();

    ProductDTO findById(UUID id);

    ProductDTO create(ProductDTO productDTO);

    ProductDTO update(UUID id, ProductDTO productDTO);

    void delete(UUID id);
}
