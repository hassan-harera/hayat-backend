package com.harera.hayat.gateway;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@EnableWebFlux
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(r -> r.path("/api/v1/donations/**")
                        .uri("lb://donations-service:8084"))
                .route(r -> r.path("api/v1/needs/**")
                        .uri("lb://needs-service:8082"))
                .route(r -> r.path("/api/v1/cities/**")
                        .and()
                        .path("/api/v1/states/**")
                        .uri("lb://common-service"))
                .route(r -> r.path("/api/v1/auth/**")
                        .uri("http://authorization-service"))
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
