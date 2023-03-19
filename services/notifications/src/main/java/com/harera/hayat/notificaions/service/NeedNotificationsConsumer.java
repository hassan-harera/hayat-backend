package com.harera.hayat.notificaions.service;

import com.harera.hayat.notificaions.model.need.BloodNeed;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class NeedNotificationsConsumer {

    @RabbitListener(queues = "${queue.donations.processing}")
    public void consumeBloodNeeds(BloodNeed bloodNeed) {
        log.debug("Received Message: " + bloodNeed);
    }
}
