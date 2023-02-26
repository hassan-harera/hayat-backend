package com.harera.hayat.authorization.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ReactiveConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
