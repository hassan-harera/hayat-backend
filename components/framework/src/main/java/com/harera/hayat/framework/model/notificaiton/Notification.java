package com.harera.hayat.framework.model.notificaiton;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class Notification implements Serializable {

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("device_token")
    private String deviceToken;
}
