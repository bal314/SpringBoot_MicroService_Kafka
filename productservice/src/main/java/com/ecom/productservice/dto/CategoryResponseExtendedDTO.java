package com.ecom.productservice.dto;

import lombok.Data;

import java.util.List;
@Data
public class CategoryResponseExtendedDTO {

    private Long id;
    private String name;
    private String description;
    private List<ProductResponseDTO> products;

}
