package com.harera.hayat.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] OPEN_APIS = { "/api/v1/cities/**", "/api/v1/states/**",
            "/actuator/**", "/api/v1/clothing/seasons/**", "/api/v1/clothing/sizes/**",
            "/api/v1/clothing/conditions/**", "/api/v1/clothing/types/**",
            "/api/v1/food/units/**", "/api/v1/food/categories/**",
            "/api/v1/clothing/condition/**", "/v3/api-docs/**", "/swagger-ui/**",
            "/swagger-ui.html", "/webjars/**", "/swagger-resources/**" };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                        .authorizeHttpRequests().requestMatchers(OPEN_APIS).permitAll()
                        .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                        .build();
    }
}
