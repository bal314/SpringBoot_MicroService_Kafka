package com.ecom.productservice.controller;

import com.ecom.productservice.dto.CategoryRequestDTO;
import com.ecom.productservice.dto.CategoryResponseDTO;
import com.ecom.productservice.dto.CategoryResponseExtendedDTO;
import com.ecom.productservice.service.CategoryService;
import com.ecom.productservice.service.impl.CategoryServiceImpl;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
     public CategoryResponseDTO createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        return categoryService.createCategory(categoryRequestDTO);
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDTO getCategoryById(@PathVariable Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @GetMapping
    public List<CategoryResponseExtendedDTO> getCategory(){
        return categoryService.getAllCategories();
    }

    @PutMapping("/{categoryId}")
    public CategoryResponseDTO updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDTO categoryRequestDTO){
        return categoryService.updateCategory(categoryId, categoryRequestDTO);
    }

    @DeleteMapping("/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId){
        return  categoryService.deleteCategory(categoryId);
    }
}
