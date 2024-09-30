package com.zett.springbootmvc.dtos.product;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 100, message =  "Name must be between 3 and 100 characters")
    private String name;
    
    @Length(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Range(min = 0 , message = "Price must be greater than or equal to 0")
    private double price;

    @NotNull(message = "Stock is required")
    @Range(min = 0, message = "Stock must be greater than or equal to 0")
    private int stock;

    private UUID categoryId;
}
