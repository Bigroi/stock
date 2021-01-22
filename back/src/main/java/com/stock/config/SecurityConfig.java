package com.stock.config;

import com.stock.security.GdprWebSecurityConfigurerAdapter;
import com.stock.security.JwtAuthenticationEntryPoint;
import com.stock.security.JwtRequestFilter;
import com.stock.security.JwtTokenProcessor;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity.access}")
    private long accessTokenValidity;

    @Value("${jwt.validity.refresh}")
    private long refreshTokenValidity;

    @Bean
    public JwtRequestFilter createJwtRequestFilter(
            UserService userService,
            JwtTokenProcessor jwtTokenProcessor
    ) {
        return new JwtRequestFilter(userService, jwtTokenProcessor);
    }

    @Bean
    public JwtTokenProcessor createJwtTokenProcessor() {
        return new JwtTokenProcessor(secret, accessTokenValidity, refreshTokenValidity);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationEntryPoint createJwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public GdprWebSecurityConfigurerAdapter createGdprWebSecurityConfigurerAdapter(
            JwtAuthenticationEntryPoint entryPoint,
            JwtRequestFilter jwtRequestFilter
    ) {
        return new GdprWebSecurityConfigurerAdapter(entryPoint, jwtRequestFilter);
    }

}
