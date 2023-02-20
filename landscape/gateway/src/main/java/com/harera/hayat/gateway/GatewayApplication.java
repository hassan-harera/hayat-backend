package com.harera.hayat.gateway;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/v1/needs/**")
                        .uri("lb://NEEDS-SERVICE"))
                .route(r -> r.path("/api/v1/auth/**")
                        .and().path("/api/v1/oauth/**")
                        .uri("lb://AUTHORIZATION-SERVICE"))
                .build();
    }
}
