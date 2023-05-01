package com.notif.notification;

import com.notif.notification.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {
	@Autowired
	private KafkaProducer kafkaProducer;
	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

	public void sendKafkaMessage(String message) {
		kafkaProducer.sendMessage(message);
	}
}
