package com.zett.springbootmvc.dtos.product;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;

    @NotNull
    private String name;
    
    @Length(max = 1000)
    private String description;

    @NotNull
    private double price;
}
