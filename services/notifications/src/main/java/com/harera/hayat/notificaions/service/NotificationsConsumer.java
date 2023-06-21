package com.harera.hayat.notificaions.service;

import com.harera.hayat.framework.model.notificaiton.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationsConsumer {

    private final FirebaseService firebaseService;
    public NotificationsConsumer(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.notifications}")
    public void consumeNotifications(Notification notification) {
        firebaseService.send(notification);
        log.error("Notification Sent: " + notification);
    }
}
