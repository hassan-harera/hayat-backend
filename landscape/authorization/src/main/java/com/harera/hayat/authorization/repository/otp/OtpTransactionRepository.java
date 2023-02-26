package com.harera.hayat.authorization.repository.otp;

import com.harera.hayat.authorization.model.otp.OtpTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpTransactionRepository extends JpaRepository<OtpTransaction, Long> {
}
