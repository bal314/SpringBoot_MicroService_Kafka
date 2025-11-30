package com.ecom.productservice.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean inStock;
    private Long categoryId;
}
