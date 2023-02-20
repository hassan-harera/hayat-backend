package com.harera.hayat.authorization;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.harera.hayat.*" })
public class AuthorizationApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(AuthorizationApplication.class,
                        args);
    }
}
