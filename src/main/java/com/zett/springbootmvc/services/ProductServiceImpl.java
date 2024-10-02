package com.zett.springbootmvc.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.zett.springbootmvc.dtos.category.CategoryDTO;
import com.zett.springbootmvc.dtos.product.ProductCreateDTO;
import com.zett.springbootmvc.dtos.product.ProductDTO;
import com.zett.springbootmvc.entities.Category;
import com.zett.springbootmvc.entities.Product;
import com.zett.springbootmvc.repositories.ProductRepository;

import jakarta.persistence.criteria.Predicate;

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
            productDTO.setStock(product.getStock());

            if (product.getCategory() != null) {
                productDTO.setCategoryId(product.getCategory().getId());

                // Convert category to categoryDTO
                var categoryDTO = new CategoryDTO();
                categoryDTO.setId(product.getCategory().getId());
                categoryDTO.setName(product.getCategory().getName());
                categoryDTO.setDescription(product.getCategory().getDescription());

                // Set categoryDTO to productDTO
                productDTO.setCategory(categoryDTO);
            }
            return productDTO;
        }).toList();

        return productsDTO;
    }

    @Override
    public List<ProductDTO> findAll(String keyword) {
        Specification<Product> specification = (root, query, criteriaBuilder) ->{
            if (keyword == null) {
                return null;
            }
            // WHERE LOWER(name) LIKE %keyword%
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%");
                    
            // WHERE LOWER(description) LIKE %keyword%
            Predicate desPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");

            return criteriaBuilder.or(namePredicate, desPredicate);
        };

        var products = productRepository.findAll(specification);

        //convert List<Product> to List<ProductDTO>
        var productsDTO = products.stream().map(product -> {
            var productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setStock(product.getStock());

            if (product.getCategory() != null) {
                productDTO.setCategoryId(product.getCategory().getId());

                // Convert category to categoryDTO
                var categoryDTO = new CategoryDTO();
                categoryDTO.setId(product.getCategory().getId());
                categoryDTO.setName(product.getCategory().getName());
                categoryDTO.setDescription(product.getCategory().getDescription());

                // Set categoryDTO to productDTO
                productDTO.setCategory(categoryDTO);
            }
            return productDTO;
        }).toList();

        return productsDTO;
    }

    @Override
    public Page<ProductDTO> findAll(String keyword, Pageable pageable) {
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            if (keyword == null) {
                return null;
            }

            // WHERE LOWER(name) LIKE %keyword%
            Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(description) LIKE %keyword%
            Predicate desPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + keyword.toLowerCase() + "%");

            // WHERE LOWER(name) LIKE %keyword% OR LOWER(description) LIKE %keyword%
            return criteriaBuilder.or(namePredicate, desPredicate);
        };


        var products = productRepository.findAll(specification, pageable);

        // Covert List<Category> to List<CategoryDTO>
        var productDTOs = products.map(product -> {
            var productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setStock(product.getStock());

            if (product.getCategory() != null) {
                productDTO.setCategoryId(product.getCategory().getId());

                // Convert category to categoryDTO
                var categoryDTO = new CategoryDTO();
                categoryDTO.setId(product.getCategory().getId());
                categoryDTO.setName(product.getCategory().getName());
                categoryDTO.setDescription(product.getCategory().getDescription());

                // Set categoryDTO to productDTO
                productDTO.setCategory(categoryDTO);
            }
            return productDTO;
        });

        return productDTOs;
        
    }


    @Override
    public ProductDTO findById(UUID id) {
        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return null;
        }

        var productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());

        if (product.getCategory() != null) {
            productDTO.setCategoryId(product.getCategory().getId());

            // Convert category to categoryDTO
            var categoryDTO = new CategoryDTO();
            categoryDTO.setId(product.getCategory().getId());
            categoryDTO.setName(product.getCategory().getName());
            categoryDTO.setDescription(product.getCategory().getDescription());

            // Set categoryDTO to productDTO
            productDTO.setCategory(categoryDTO);
        }

        return productDTO;
    }

    @Override
    public ProductDTO create(ProductCreateDTO productCreateDTO) {
        if (productCreateDTO == null) {
            throw new IllegalArgumentException("Required product");
        }

        var existingProduct = productRepository.findByName(productCreateDTO.getName());
        if (existingProduct != null) {
            throw new IllegalArgumentException("Product already exists");
        }

        var product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStock(productCreateDTO.getStock());

        if (productCreateDTO.getCategoryId() != null) {
            // Find category by id
            var category = new Category();
            // Set category to product
            category.setId(productCreateDTO.getCategoryId());
            // Set category to product
            product.setCategory(category);
        }

        productRepository.save(product);

        var newProductDTO = new ProductDTO();
        newProductDTO.setId(product.getId());
        newProductDTO.setName(product.getName());
        newProductDTO.setDescription(product.getDescription());
        newProductDTO.setPrice(product.getPrice());
        newProductDTO.setStock(product.getStock());

        if (product.getCategory() != null) {
            newProductDTO.setCategoryId(product.getCategory().getId());
        }

        return newProductDTO;
    }

    @Override
    public ProductDTO update(UUID id, ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("ProductDTO is required");
        }

        // Find product by id - Managed
        var product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }

        // Update product
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        // Kiem tra xem category co duoc select hay khong
        // Neu co thi product co category va can set category cho product do de update
        if (productDTO.getCategoryId() != null) {
            var category = new Category();
            category.setId(productDTO.getCategoryId());
            product.setCategory(category);
        }

        // Save product => update
        product = productRepository.save(product);

        // Convert Product to ProductDTO
        var updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(product.getId());
        updatedProductDTO.setName(product.getName());
        updatedProductDTO.setDescription(product.getDescription());
        updatedProductDTO.setPrice(product.getPrice());
        updatedProductDTO.setStock(product.getStock());

        // Neu product co category thi set category id cho productDTO
        if (product.getCategory() != null) {
            updatedProductDTO.setCategoryId(product.getCategory().getId());
        }

        return updatedProductDTO;
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
