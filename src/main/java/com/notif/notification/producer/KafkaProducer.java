package com.notif.notification.producer;


import com.notif.notification.model.KafkaNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private static final String TOPIC = "send";

    @Autowired
    private KafkaTemplate<String, KafkaNotification> kafkaTemplate;

    public void sendMessage(String content) {
        KafkaNotification message = new KafkaNotification(content);
        kafkaTemplate.send(TOPIC, message);
    }
}