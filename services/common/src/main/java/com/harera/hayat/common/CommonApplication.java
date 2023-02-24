package com.harera.hayat.common;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.harera.hayat.*"})
public class CommonApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(CommonApplication.class, args);
    }
}
