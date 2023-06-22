package com.harera.hayat.needs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
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

    private static final String[] OPEN_APIS = { "/api/v1/auth/**", "/api/v1/oauth/**",
            "/actuator/prometheus/**", "/actuator/**", "/v3/api-docs/**",
            "/swagger-ui/**", "/swagger-ui.html", "/actuator/**", "/webjars/**",
            "/swagger-resources/**" };

    @Bean
    @Profile({ "default", "prod" })
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests().requestMatchers(OPEN_APIS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/needs/**").permitAll()
                        .anyRequest().authenticated().and()
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
