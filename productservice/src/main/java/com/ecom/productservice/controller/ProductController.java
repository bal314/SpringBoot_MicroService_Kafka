package com.ecom.productservice.controller;

import com.ecom.productservice.dto.*;
import com.ecom.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO productRequestDTO){
        return productService.createProduct(productRequestDTO);
    }

    @GetMapping("/{productId}")
    public ProductResponseDTO getProductById(@PathVariable Long productId){
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<ProductResponseDTO> getProducts(){
        return productService.getAllProduct();
    }

    @PatchMapping("/{productId}/stock")
    public ProductResponseDTO updateProduct(@PathVariable Long productId, @RequestParam Integer quantity){
        return productService.updateStock(productId, quantity);
    }

    @DeleteMapping("/{productId}")
    public String deleteProduct(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }

}
