package com.harera.hayat.authorization.model.otp;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@RedisHash(value = "OTP", timeToLive = 1800)
public class OTP implements Serializable {

    private LocalDateTime timestamp;
    private String id;
    private String otp;

    public OTP(LocalDateTime timestamp, String mobile, String otp) {
        this.timestamp = timestamp;
        this.otp = otp;
        this.id = mobile;
    }
}