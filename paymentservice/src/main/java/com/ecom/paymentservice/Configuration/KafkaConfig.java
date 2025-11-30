package com.ecom.paymentservice.Configuration;

import com.ecom.paymentservice.kafkaevents.PaymentCompletedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
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
    public ProducerFactory<String, PaymentCompletedEvent> producerFactory() {
        var configProp = new HashMap<String, Object>();
        configProp.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
        configProp.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configProp.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return new DefaultKafkaProducerFactory<>(configProp);
    }

    @Bean
    public KafkaTemplate<String,PaymentCompletedEvent> kafkaTemplate(){
        return new KafkaTemplate<String, PaymentCompletedEvent>(producerFactory());
    }

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name("topic")
                .partitions(2)
                .replicas(2). build();
    }
}
