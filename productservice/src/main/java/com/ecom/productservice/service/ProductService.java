package com.ecom.productservice.service;

import com.ecom.productservice.dto.ProductRequestDTO;
import com.ecom.productservice.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

     ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
     ProductResponseDTO getProductById(Long productId);
     List<ProductResponseDTO> getAllProduct();
     ProductResponseDTO updateStock(Long productId, Integer stockQuantity);

     String deleteProduct(Long productId);
}
