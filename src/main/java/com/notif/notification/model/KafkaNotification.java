package com.notif.notification.model;
public class KafkaNotification {
    private String content;

    public KafkaNotification() {
    }

    public KafkaNotification(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}