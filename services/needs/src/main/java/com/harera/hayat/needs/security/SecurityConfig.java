package com.harera.hayat.needs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] OPEN_APIS = {
            "/actuator/**",
            "/api/v1/auth/**", "/api/v1/oauth/**", "/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**"};

    @Bean
    @Profile({"default", "prod"})
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(smc -> {
            smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });
        http.authorizeHttpRequests(ahrc -> {
            ahrc.requestMatchers(OPEN_APIS).permitAll();
            ahrc.requestMatchers("/api/**").access(AccessController::checkAccess);
            ahrc.anyRequest().fullyAuthenticated(); //
        });
        http.oauth2ResourceServer().jwt();
        return http.build();
    }

    @Bean
    @Profile("dev")
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable().formLogin().disable().httpBasic().disable().csrf().disable()
                        .authorizeHttpRequests().requestMatchers("/**").permitAll()
                        .anyRequest().authenticated();
        return http.build();
    }
}
