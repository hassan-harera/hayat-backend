package com.harera.hayat.needs.service;

import com.harera.hayat.framework.model.notificaiton.Notification;
import com.harera.hayat.needs.model.Need;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@Service
@Log4j2
public class NeedNotificationsService {

    private final RabbitTemplate rabbitTemplate;
    private final String notificationsQueue;

    public NeedNotificationsService(RabbitTemplate rabbitTemplate, @Value("${spring.rabbitmq.queue.notifications}") String notificationsQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationsQueue = notificationsQueue;
    }

    public void notifyProcessingNeed(Need need) {
        Notification notification = createProcessingDonationNotification(need);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createProcessingDonationNotification(Need donation) {
        Notification notification = new Notification();
        notification.setTitle("Need post is under review");
        notification.setBody("We are reviewing your need post, please wait");
        notification.setDeviceToken(donation.getUser().getDeviceToken());
        notification.setUserId(donation.getUser().getId());
        return notification;
    }

    private void sendNotification(Notification notification) {
        rabbitTemplate.convertAndSend(notificationsQueue, notification);
    }

    public void notifyProcessingDonation(Need donation) {
        Notification notification = createProcessingDonationNotification(donation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    public void notifyDonationAccepted(Need bookDonation) {
        Notification notification = createDonationAcceptedNotification(bookDonation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createDonationAcceptedNotification(Need bookDonation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is accepted");
        notification.setBody("Your donation is accepted, people can now request it");
        notification.setDeviceToken(bookDonation.getUser().getDeviceToken());
        notification.setUserId(bookDonation.getUser().getId());
        return notification;
    }

    public void notifyDonationRejected(Need bookDonation) {
        Notification notification = createDonationRejectedNotification(bookDonation);
        try {
            sendNotification(notification);
        } catch (Exception e) {
            log.error("Error while sending notification: " + notification, e);
        }
    }

    private Notification createDonationRejectedNotification(Need bookDonation) {
        Notification notification = new Notification();
        notification.setTitle("Donation is rejected");
        notification.setBody("Your donation is rejected, please check your donation");
        notification.setDeviceToken(bookDonation.getUser().getDeviceToken());
        notification.setUserId(bookDonation.getUser().getId());
        return notification;
    }
}
