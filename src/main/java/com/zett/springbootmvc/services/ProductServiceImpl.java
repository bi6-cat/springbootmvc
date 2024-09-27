package com.zett.springbootmvc.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.zett.springbootmvc.dtos.product.ProductDTO;
import com.zett.springbootmvc.entities.Product;
import com.zett.springbootmvc.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAll() {
        var products = productRepository.findAll();

        var productsDTO = products.stream().map(product -> {
            var productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            return productDTO;
        }).toList();

        return productsDTO;
    }

    @Override
    public ProductDTO findById(UUID id) {
        var product = productRepository.findById(id).orElse(null);

        if(product == null){
            return null;
        }

        var productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        
        return productDTO;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        if(productDTO == null){
            throw new IllegalArgumentException("Required productDTO");
        }

        var existingProduct = productRepository.findByName(productDTO.getName());
        if(existingProduct != null){
            throw new IllegalArgumentException("Product already exists");
        }

        var product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        productDTO.setId(product.getId());

        return productDTO;
    }

    @Override
    public ProductDTO update(UUID id, ProductDTO productDTO) {
        var product = productRepository.findById(id).orElse(null);

        if(product == null){
            return null;
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        productRepository.save(product);

        productDTO.setId(product.getId());

        return productDTO;
    }

    @Override
    public void delete(UUID id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        productRepository.deleteById(id);
    }

}
