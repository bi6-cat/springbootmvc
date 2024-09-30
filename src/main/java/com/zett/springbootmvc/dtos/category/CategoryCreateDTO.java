package com.zett.springbootmvc.dtos.category;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateDTO {

    @NotNull(message = "Name is required")

    @Length(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Length(max = 1000, message = "Description must be less than 1000 characters")
    private String description;
}
