package com.ecom.productservice.service.impl;

import com.ecom.productservice.dto.ProductRequestDTO;
import com.ecom.productservice.dto.ProductResponseDTO;
import com.ecom.productservice.entity.Category;
import com.ecom.productservice.entity.Product;
import com.ecom.productservice.mapper.ProductMapper;
import com.ecom.productservice.repository.CategoryRepository;
import com.ecom.productservice.repository.ProductRepository;
import com.ecom.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    public final ProductRepository productRepository;
    @Autowired
    public final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.getCategoryId())
                .orElseThrow(()-> new RuntimeException("category not found"));
        Product product = ProductMapper.toProductEntity(productRequestDTO);
        product.setCategory(category);
        product = productRepository.save(product);
        return ProductMapper.toProductResponseDTO(product);
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("product not fount"));
        return ProductMapper.toProductResponseDTO(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProduct() {
        return productRepository.findAll().stream().map(this::convertDTO).toList();
    }

    @Override
    public ProductResponseDTO updateStock(Long productId, Integer stockQuantity) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("product not fount"));
        product.setStockQuantity(product.getStockQuantity()+stockQuantity);
        product = productRepository.save(product);
        return ProductMapper.toProductResponseDTO(product);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("category not found"));
        productRepository.deleteById(productId);
        return "product deleted successfully";
    }
    private ProductResponseDTO convertDTO(Product product){
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProductId(product.getProductId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setCategoryName(product.getCategory().getName());
        productResponseDTO.setStockQuantity(product.getStockQuantity());
        productResponseDTO.setInStock(product.getInStock());
        return productResponseDTO;
    }
}
