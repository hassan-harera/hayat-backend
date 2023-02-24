package com.harera.hayat.needs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable().formLogin().disable().httpBasic().disable().csrf().disable()
                .authorizeHttpRequests().requestMatchers("/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }
}