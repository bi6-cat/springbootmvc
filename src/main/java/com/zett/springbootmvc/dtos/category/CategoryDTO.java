package com.zett.springbootmvc.dtos.category;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private UUID id;

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 100, message =  "Name must be between 3 and 100 characters")
    private String name;
    
    @Length(max = 1000, message = "Description must be less than 1000 characters")
    private String description;
}
