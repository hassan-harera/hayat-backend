package com.harera.hayat.authorization.model.otp;


import com.harera.hayat.framework.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "otp_transaction")
public class OtpTransaction extends BaseEntity {

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
