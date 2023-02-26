package com.harera.hayat.authorization.service.sms;

import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SmsService {

    @Value("${sms.schema.config.url}")
    private String url;
    @Value("${sms.config.username}")
    private String userName;
    @Value("${sms.config.password}")
    private String password;

    @Autowired
    private WebClient webClient;

    private void sendSms(String mobile, String code) {
        webClient
                .post()
                .uri(getUrl(mobile, code))
                .retrieve()
                .bodyToFlux(String.class)
                .doOnError((error) -> log.error("SMS to %s sent failed".formatted(mobile)))
                .doOnComplete(() -> log.debug("SMS sent successfully"));
    }


    private String getUrl(String mobile, String code) {
        String url = null;
        try {
            URIBuilder b = new URIBuilder(this.url);
            b.addParameter("Username", userName);
            b.addParameter("password", password);
            b.addParameter("Mobile", "2" + mobile);
            b.addParameter("Code", code);
            url = b.toString();
            log.debug("Execute SMS API with the following url: " + url);
        } catch (URISyntaxException e) {
            // just ignore me
        }
        return url;
    }

    public void sendOtp(String mobile, String otp) {
        sendSms(mobile, otp);
    }
}
