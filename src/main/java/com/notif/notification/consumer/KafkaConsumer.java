package com.notif.notification.consumer;


import com.notif.notification.model.KafkaNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @KafkaListener(topics = "send", groupId = "my-consumer-group")
    public void consumeMessage(KafkaNotification message) {
        System.out.println("Received message: " + message.getContent());
    }
}