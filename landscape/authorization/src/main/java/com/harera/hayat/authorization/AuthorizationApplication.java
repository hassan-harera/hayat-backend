package com.harera.hayat.authorization;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = { "com.harera.hayat.*" })
public class AuthorizationApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AuthorizationApplication.class,
                        args);
    }
}
