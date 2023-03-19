package com.harera.hayat.notificaions.service;

import com.harera.hayat.notificaions.model.Notification;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class NotificationsConsumer {

    @RabbitListener(queues = "${queue.donations.processing}")
    public void consumeProcessingDonations(Notification user) {
        log.error("Received Message: " + user);
    }

    @RabbitListener(queues = "${queue.donations.processing}")
    public void consumeAcceptedDonations(Notification user) {
        log.error("Received Message: " + user);
    }

}
