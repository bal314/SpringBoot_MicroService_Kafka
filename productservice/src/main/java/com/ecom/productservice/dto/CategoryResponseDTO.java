package com.ecom.productservice.dto;

import com.ecom.productservice.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDTO {

    private Long id;
    private String name;
    private String description;
}
