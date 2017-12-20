package com.bigroi.stock.dispatcher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.bigroi.stock.json.ResultBean;

public class AuthenticationHandler 
	implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request, 
			HttpServletResponse response, 
			AuthenticationException authentication)
					throws IOException, ServletException {
		response.getWriter().append(new ResultBean(-1, authentication.getMessage()).toString());
	}

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication)
					throws IOException, ServletException {
		response.getWriter().append(new ResultBean(0, "/Index.spr").toString());
	}


}