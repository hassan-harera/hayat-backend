package com.harera.hayat.authorization.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] OPEN_APIS = { "/api/v1/auth/**", "/api/v1/oauth/**",
            "/api/v1/auth/password-reset", "/api/v1/auth/logout", "/api/v1/otp/**",
            "/actuator/**", "/actuator/prometheus/**", "/v3/api-docs/**",
            "/swagger-ui/**", "/swagger-ui.html", "/webjars/**",
            "/swagger-resources/**" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and().formLogin().disable().httpBasic().disable()
                        .authorizeHttpRequests().requestMatchers(OPEN_APIS).permitAll()
                        .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                        new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
