package com.ecom.orderservice.config;

import com.ecom.orderservice.events.OrderCreatedEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootStrapServer;

    @Bean
    public ProducerFactory<String, OrderCreatedEvent> producerFactory(){
        var configProp = new HashMap<String, Object>();
        configProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        configProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(configProp);
    }

    @Bean
    public KafkaTemplate<String,OrderCreatedEvent> kafkaTemplate(){
        return new KafkaTemplate<String, OrderCreatedEvent>(producerFactory());
    }
}
