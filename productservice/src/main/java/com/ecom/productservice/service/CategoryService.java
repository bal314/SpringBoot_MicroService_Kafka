package com.ecom.productservice.service;

import com.ecom.productservice.dto.CategoryRequestDTO;
import com.ecom.productservice.dto.CategoryResponseDTO;
import com.ecom.productservice.dto.CategoryResponseExtendedDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO getCategoryById(Long categoryId);
    List<CategoryResponseExtendedDTO> getAllCategories();
    CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO);
    String deleteCategory(Long categoryId);

}
