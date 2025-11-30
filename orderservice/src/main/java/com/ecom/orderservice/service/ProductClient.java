package com.ecom.orderservice.service;

import com.ecom.orderservice.dto.ProductResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

    private final RestTemplate restTemplate;


    public  ProductClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public ProductResponseDTO getProduct(Long productId){
        String url = "http://productservice/api/product/"+productId;
        return restTemplate.getForObject(url, ProductResponseDTO.class);
    }

    public void updateStock(Long productId, Integer quantity){
        String url = "http://productservice/api/product/"+productId+"/stock?quantity="+quantity;
         restTemplate.patchForObject(url, null, Void.class);
    }
}
