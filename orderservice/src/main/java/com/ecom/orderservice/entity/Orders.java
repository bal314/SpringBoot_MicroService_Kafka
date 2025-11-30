package com.ecom.orderservice.entity;

import com.ecom.orderservice.dto.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    private String orderId;
    private String customerId;
    private LocalDateTime orderDate;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
