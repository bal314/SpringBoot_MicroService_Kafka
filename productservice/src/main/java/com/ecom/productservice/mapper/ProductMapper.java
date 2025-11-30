package com.ecom.productservice.mapper;

import com.ecom.productservice.dto.ProductRequestDTO;
import com.ecom.productservice.dto.ProductResponseDTO;
import com.ecom.productservice.entity.Product;

public class ProductMapper {


    public static ProductResponseDTO toProductResponseDTO(Product product) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO();
        productResponseDTO.setProductId(product.getProductId());
        productResponseDTO.setName(product.getName());
        productResponseDTO.setDescription(product.getDescription());
        productResponseDTO.setPrice(product.getPrice());
        productResponseDTO.setStockQuantity(product.getStockQuantity());
        productResponseDTO.setInStock(product.getInStock());
        productResponseDTO.setCategoryName(product.getCategory().getName());
        return productResponseDTO;
    }

    public static Product toProductEntity(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setStockQuantity(productRequestDTO.getStockQuantity());
        product.setInStock(productRequestDTO.getInStock());
        return product;
    }
}
