package com.bigroi.stock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bigroi.stock.json.ResultBean;

@Repository
@Service
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

	public static String getMainPage(String contexRoot){
		switch (contexRoot){
		case "": return "/product/List.spr";
		case "/Transport": return "/Transport/deal-list.spr";
		default : return contexRoot + "/Index.spr";
		}
	}
	
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
		response.getWriter().append(new ResultBean(0, getMainPage(request.getContextPath()), null).toString());
	}

	public static String getApplicationType(String contexRoot) {
		switch (contexRoot){
		case "": return "TRADER";
		case "/Transport": return "TRANSPORT";
		default : return "";
		}
	}


}
