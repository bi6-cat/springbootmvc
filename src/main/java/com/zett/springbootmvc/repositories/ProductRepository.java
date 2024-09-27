package com.zett.springbootmvc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zett.springbootmvc.entities.Product;

//Register this interface as a bean in the Spring context
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    Product findByName(String name);
}
