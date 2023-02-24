package com.harera.hayat.donations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.harera.hayat.*"})
public class DonationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DonationsApplication.class, args);
    }
}
