package com.bigroi.transport.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.bigroi.transport.dispatcher.AuthenticationHandlerTransport;
import com.bigroi.transport.service.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfigurationTransport extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationHandlerTransport authenticationHandler;
	
	@Autowired
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setAuthenticationHandler(AuthenticationHandlerTransport authenticationHandler) {
		this.authenticationHandler = authenticationHandler;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/index.spr")
				.failureHandler(authenticationHandler)
				.successHandler(authenticationHandler)
				.loginProcessingUrl("/account/json/login.spr")
			.and()
			.httpBasic()
			.and()
			.logout()
				.logoutSuccessUrl("/index.spr")
				.logoutUrl("/account/json/logout.spr")
			.and()
			.csrf().disable()
			.exceptionHandling()
				.accessDeniedPage("/index.spr")
			.and();
	}

	@Bean("daoAuthenticationProvider")
	public DaoAuthenticationProvider getDaoAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		return provider;
	}

}
