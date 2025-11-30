package com.ecom.orderservice.service;

import com.ecom.orderservice.dto.*;
import com.ecom.orderservice.entity.Orders;
import com.ecom.orderservice.entity.OrderItem;
import com.ecom.orderservice.events.OrderCreatedEvent;
import com.ecom.orderservice.repository.OrderItemRepository;
import com.ecom.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    ProductClient productClient;

    @Autowired
    KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public OrderResponseDTO placeOrder(OrderRequestDTO orderRequestDTO){
        // generate order id
        String orderId = generateOrderId();
        double totalPrice = 0.0;
        List<OrderItemRequestDTO>  itemRequestDTOs = orderRequestDTO.getItems();
        //create order
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        List<OrderItemResponseDTO> itemsResponseDTOs = new ArrayList<OrderItemResponseDTO>();

        for(OrderItemRequestDTO itemRequestDTO : itemRequestDTOs) {
            ProductResponseDTO product = productClient.getProduct(itemRequestDTO.getProductId());
            if(product.getStockQuantity()<itemRequestDTO.getQuantity()){
                throw new RuntimeException("insufficient item");
            }
            productClient.updateStock(itemRequestDTO.getProductId(), -itemRequestDTO.getQuantity());
            double price = itemRequestDTO.getQuantity()*product.getPrice();
            totalPrice = totalPrice+price;

            OrderItem orderItem = new OrderItem(generateOrderItemId(), orderId, itemRequestDTO.getProductId(),
                    itemRequestDTO.getQuantity(),product.getPrice());
            orderItems.add(orderItem);

           // OrderItemResponseDTO orderItemResponseDTO =

        }

        Orders orders = new Orders(orderId,orderRequestDTO.getCustomerId(), LocalDateTime.now(),totalPrice, OrderStatus.PENDING);
        orderRepository.save(orders);
        orderItemRepository.saveAll(orderItems);
        // sending the kafka event to kafka topic of cluster
        placeOrder(orders);

        return new OrderResponseDTO(orders.getOrderId(), orders.getCustomerId(), orders.getOrderDate(),
        orders.getAmount(), orders.getStatus(),orderItems);
    }

    public  OrderResponseDTO getOrderByOrderId(String orderId){
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);

        return new OrderResponseDTO(orders.getOrderId(), orders.getCustomerId(), orders.getOrderDate(),
                orders.getAmount(), orders.getStatus(),orderItems);
    }

    public List<OrderResponseDTO> getOrderByCustomer(String customerId){
        List<Orders> orders = orderRepository.findAllByCustomerId(customerId);
        List<OrderResponseDTO> orderResponseDTOList = new ArrayList<OrderResponseDTO>();
        orders.forEach(order -> {
            List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(order.getOrderId());
            orderResponseDTOList.add(new OrderResponseDTO(order.getOrderId(),order.getCustomerId(),order.getOrderDate(),
                    order.getAmount(),order.getStatus(),orderItems));
        });

        return orderResponseDTOList;
    }

    public void updateOrderStatus(String orderId, OrderStatus orderStatus){
        Orders orders = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        orders.setStatus(orderStatus);
        orderRepository.save(orders);
    }

    public void placeOrder(Orders orders){
        try {
            OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(orders.getOrderId(),
                    orders.getCustomerId(), String.valueOf(orders.getAmount()));
            kafkaTemplate.send("order-event", orderCreatedEvent);
            log.info("Successful sent orderCreatedEvent to kafka listener");
        } catch (Exception e){
            log.error("Error in sending orderCreatedEvent: {}",e.getMessage());
            throw new RuntimeException("Error in sending orderCreatedEvent", e);
        }
    }
    private String generateOrderId(){
        return "ord"+ UUID.randomUUID().toString().substring(0,8);
    }

    private String generateOrderItemId(){
        return "ord_item"+ UUID.randomUUID().toString().substring(0,8);
    }
}
