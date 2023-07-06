package com.harera.hayat.authorization.service;

import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.framework.model.notificaiton.Notification;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthorizationNotificationsService {

    private final RabbitTemplate rabbitTemplate;
    private final String notificationsQueue;

    public AuthorizationNotificationsService(RabbitTemplate rabbitTemplate,
                    @Value("${spring.rabbitmq.queue.notifications}") String notificationsQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationsQueue = notificationsQueue;
    }

    public void notifyNewLoginDetected(User user) {
        Notification notification = createNewLoginNotification(user);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private void sendNotification(Notification notification) {
        rabbitTemplate.convertAndSend(notificationsQueue, notification);
    }

    private Notification createNewLoginNotification(User user) {
        Notification notification = new Notification();
        notification.setTitle("New login detected");
        notification.setBody("We detected a new login to your account");
        notification.setDeviceToken(user.getDeviceToken());
        notification.setUserId(user.getId());
        return notification;
    }
}
