package com.harera.hayat.classifier;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.harera.hayat.*"})
public class ClassifierApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ClassifierApplication.class, args);
    }
}
