package com.zett.springbootmvc.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.zett.springbootmvc.entities.Product;

//Register this interface as a bean in the Spring context
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    List<Product> findByNameContainingIgnoreCase(String name);
    Product findByName(String name);
}
