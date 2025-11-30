package com.ecom.productservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Boolean inStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private  Category category;

    @PrePersist
    @PreUpdate
    public void updateStockStatus(){
        inStock = this.stockQuantity!=null && this.stockQuantity>0;
        if(createdAt==null){
            createdAt= LocalDateTime.now();
        }
        updatedAt=LocalDateTime.now();
    }


}
