package com.ecom.productservice.mapper;

import com.ecom.productservice.dto.CategoryRequestDTO;
import com.ecom.productservice.dto.CategoryResponseDTO;
import com.ecom.productservice.dto.CategoryResponseExtendedDTO;
import com.ecom.productservice.dto.ProductResponseDTO;
import com.ecom.productservice.entity.Category;
import com.ecom.productservice.entity.Product;

import java.util.List;

public class CategoryMapper {

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setId(category.getCategoryId());
        categoryResponseDTO.setName(category.getName());
        categoryResponseDTO.setDescription(category.getDescription());
        return categoryResponseDTO;
    }

    public static Category toCategoryEntity(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        return category;
    }

    public static CategoryResponseExtendedDTO toCategoryResponseExtendedDTO(Category category) {
        CategoryResponseExtendedDTO categoryResponseExtendedDTO = new CategoryResponseExtendedDTO();
        categoryResponseExtendedDTO.setId(category.getCategoryId());
        categoryResponseExtendedDTO.setName(category.getName());
        categoryResponseExtendedDTO.setDescription(category.getDescription());
        List<ProductResponseDTO> productResponseDTOs = category.getProducts().stream().map(ProductMapper::toProductResponseDTO).toList();
        categoryResponseExtendedDTO.setProducts(productResponseDTOs);
        return categoryResponseExtendedDTO;
    }
}
