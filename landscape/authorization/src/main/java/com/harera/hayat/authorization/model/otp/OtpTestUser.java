package com.harera.hayat.authorization.model.otp;


import java.time.LocalDateTime;

import com.harera.hayat.framework.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "otp_test_user")
public class OtpTestUser extends BaseEntity {

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "otp")
    private String otp;

    @Column(name = "is_verified")
    private boolean isVerified;

    @Column(name = "is_expired")
    private boolean isExpired;

    @Column(name = "is_used")
    private boolean isUsed;

    @Column(name = "transaction_time")
    private LocalDateTime transactionTime;

    @Column(name = "transaction_expiration")
    private LocalDateTime transactionExpiration;
}
