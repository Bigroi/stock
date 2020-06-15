package com.bigroi.stock.security;

import com.bigroi.stock.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String INDEX_LINK = "/";
    private static final String LOGIN_LINK = "/account/json/Login";
    private static final String LOGOUT_LINK = "/account/json/Logout";

    private final AuthenticationHandler authenticationHandler;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(
            AuthenticationHandler authenticationHandler,
            UserService userService,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationHandler = authenticationHandler;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage(INDEX_LINK)
                .failureHandler(authenticationHandler)
                .successHandler(authenticationHandler)
                .loginProcessingUrl(LOGIN_LINK)
                .and()
                .httpBasic()
                .and()
                .logout()
                .logoutSuccessUrl(INDEX_LINK)
                .logoutUrl(LOGOUT_LINK)
                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedPage(INDEX_LINK)
                .and();
    }

    @Bean("daoAuthenticationProvider")
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }

}
