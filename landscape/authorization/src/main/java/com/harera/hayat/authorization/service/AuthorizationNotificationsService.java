package com.harera.hayat.authorization.service;

import com.harera.hayat.authorization.model.user.User;
import com.harera.hayat.framework.model.notificaiton.Notification;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Service
@Log4j2
public class AuthorizationNotificationsService {

    private final ConnectionFactory connectionFactory;
    private final String notificationsQueue;

    public AuthorizationNotificationsService(ConnectionFactory connectionFactory,
                    @Value("${spring.rabbitmq.queue.notifications}") String notificationsQueue) {
        this.connectionFactory = connectionFactory;
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

    private void sendNotification(Notification notification) throws Exception {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(notificationsQueue, false, false, false, null);
        channel.basicPublish("", notificationsQueue, null, toByteArray(notification));
        channel.close();
        connection.close();
    }

    private byte[] toByteArray(Serializable object) throws Exception {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(object);
        objectStream.flush();
        return byteStream.toByteArray();
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
