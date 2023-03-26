package com.harera.hayat.shared.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
    @Profile("prod")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().cors().and().formLogin().disable().httpBasic()
                        .disable().authorizeHttpRequests().requestMatchers(OPEN_APIS)
                        .permitAll().and()
                        .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                        .build();
    }

    @Bean
    @Profile("dev")
    public SecurityFilterChain devFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable().formLogin().disable().httpBasic().disable().csrf().disable()
                        .authorizeHttpRequests().requestMatchers("/**").permitAll()
                        .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                        new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
