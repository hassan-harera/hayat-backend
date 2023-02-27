package com.harera.hayat.shared;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.harera.hayat.*"})
public class SharedApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SharedApplication.class, args);
    }
}
