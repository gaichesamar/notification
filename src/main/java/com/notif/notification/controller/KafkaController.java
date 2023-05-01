package com.notif.notification.controller;

import com.notif.notification.model.KafkaNotification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/api")
public class KafkaController {

    private final KafkaTemplate<String, KafkaNotification> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, KafkaNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestBody KafkaNotification content) {
        kafkaTemplate.send("send", content);
        return "Message published to Kafka: " + content;
    }
    @GetMapping("/consume")
    public List<String> consumeMessages() {
        List<String> messages = new ArrayList<>();

        // Créez une instance de KafkaConsumer avec les propriétés de configuration appropriées
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);

        // Abonnez-vous au topic que vous souhaitez consommer
        kafkaConsumer.subscribe(Collections.singletonList("send"));

        // Consumez les enregistrements Kafka
        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : consumerRecords) {
            String message = record.value();
            messages.add(message);
        }

        // Fermez le consommateur Kafka après avoir consommé les messages
        kafkaConsumer.close();

        return messages;
    }
}
