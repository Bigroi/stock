package com.bigroi.stock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.bigroi.stock.dispatcher.AuthenticationHandler;
import com.bigroi.stock.service.UserService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationHandler authenticationHandler;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/Index.spr")
				.failureHandler(authenticationHandler)
				.successHandler(authenticationHandler)
				.loginProcessingUrl("/account/json/Login.spr")
			.and()
			.httpBasic()
			.and()
			.logout()
				.logoutSuccessUrl("/Index.spr")
				.logoutUrl("/account/json/Logout.spr")
			.and()
			.csrf().disable()
			.exceptionHandling()
				.accessDeniedPage("/Index.spr")
			.and();
	}

	@Bean("daoAuthenticationProvider")
	public DaoAuthenticationProvider getDaoAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userService);
		return provider;
	}
	
}
