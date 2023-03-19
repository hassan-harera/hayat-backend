package com.harera.hayat.notificaions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = { "com.harera.hayat.*" })
public class NotificationsApplications {

    public static void main(String[] args) {
        SpringApplication.run(NotificationsApplications.class, args);
    }
}
