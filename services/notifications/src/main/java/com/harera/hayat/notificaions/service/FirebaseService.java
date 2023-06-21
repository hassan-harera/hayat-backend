package com.harera.hayat.notificaions.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.harera.hayat.framework.model.notificaiton.Notification;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

import static com.google.firebase.messaging.Notification.builder;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Log4j2
@Service
public class FirebaseService {

    private final FirebaseMessaging firebaseMessaging;

    @Autowired
    public FirebaseService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void send(Notification notification) {
        Message message = buildMessage(notification);
        if (message != null) {
            try {
                firebaseMessaging.send(message);
            } catch (FirebaseMessagingException e) {
                log.error("Failed to send notification to firebase for {} with exception {}",
                                notification.getUserId(), e);
            }
            log.debug("Notification sent to firebase for {} with title {} and body {}",
                            notification.getUserId(), notification.getTitle(),
                            notification.getBody());
        }
    }

    public void sendAll(List<Notification> notificationList) {
        List<Message> messageList = buildMessages(notificationList);
        try {
            firebaseMessaging.sendAll(messageList);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send batch notifications [{}] to firebase with exception {}",
                            notificationList, e);
        }
        log.debug("Notification [{}] sent to firebase", notificationList);
    }

    private List<Message> buildMessages(List<Notification> notificationList) {
        List<Message> messageList = new LinkedList<>();
        for (Notification notification : notificationList) {
            Message message = buildMessage(notification);
            if (message != null) {
                messageList.add(message);
            }
        }
        return messageList;
    }

    private Message buildMessage(Notification notification) {
        Message message = null;
        String deviceToken = notification.getDeviceToken();
        if (isNotEmpty(deviceToken)) {
            final String title = notification.getTitle();
            final String body = notification.getBody();
            final var firebaseNotification =
                            builder().setTitle(title).setBody(body).build();

            message = Message.builder().setToken(deviceToken)
                            .setNotification(firebaseNotification)
                            .build();
        } else {
            log.debug("Notifications cannot be sent since device token is empty");
        }
        return message;
    }

}
