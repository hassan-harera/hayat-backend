package com.harera.hayat.discoveryservice;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class ServiceDiscoveryApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ServiceDiscoveryApplication.class, args);
    }
}
