package com.harera.hayat.notificaions.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private boolean active = true;

    private String title;

    private String body;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "firing_time")
    private LocalDateTime firingTime;

    @Column(name = "screen_params")
    private String screenParams;

    @Column(name = "screen_name")
    private String screenName;
}
