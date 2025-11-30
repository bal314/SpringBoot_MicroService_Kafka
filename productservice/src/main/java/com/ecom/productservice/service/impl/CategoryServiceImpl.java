package com.ecom.productservice.service.impl;

import com.ecom.productservice.dto.CategoryRequestDTO;
import com.ecom.productservice.dto.CategoryResponseDTO;
import com.ecom.productservice.dto.CategoryResponseExtendedDTO;
import com.ecom.productservice.entity.Category;
import com.ecom.productservice.mapper.CategoryMapper;
import com.ecom.productservice.repository.CategoryRepository;
import com.ecom.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = CategoryMapper.toCategoryEntity(categoryRequestDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("category not found"));
        return CategoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public List<CategoryResponseExtendedDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryMapper::toCategoryResponseExtendedDTO).toList();
    }

    @Override
    public CategoryResponseDTO updateCategory(Long categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("category not found"));
         category = CategoryMapper.toCategoryEntity(categoryRequestDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryResponseDTO(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new RuntimeException("category not found"));
        categoryRepository.deleteById(categoryId);
        return "category deleted successfully";
    }
}
