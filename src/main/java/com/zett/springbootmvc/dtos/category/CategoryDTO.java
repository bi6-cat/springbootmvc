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

    @NotNull
    private String name;
    
    @Length(max = 1000)
    private String description;
}
