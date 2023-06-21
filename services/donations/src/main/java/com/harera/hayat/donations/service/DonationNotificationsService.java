package com.harera.hayat.donations.service;

import com.harera.hayat.donations.model.BaseDonation;
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
public class DonationNotificationsService {

    private final ConnectionFactory connectionFactory;
    private final String notificationsQueue;

    public DonationNotificationsService(ConnectionFactory connectionFactory,
                    @Value("${spring.rabbitmq.queue.notifications}") String notificationsQueue) {
        this.connectionFactory = connectionFactory;
        this.notificationsQueue = notificationsQueue;
    }

    public void notifyProcessingDonation(BaseDonation donation) {
        Notification notification = createProcessingDonationNotification(donation);
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

    private Notification createProcessingDonationNotification(BaseDonation donation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is under review");
        notification.setBody("We are reviewing your donation, please wait");
        notification.setDeviceToken(donation.getUser().getDeviceToken());
        notification.setUserId(donation.getUser().getId());
        return notification;
    }
}
