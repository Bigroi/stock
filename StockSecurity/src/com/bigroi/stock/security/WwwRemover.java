package com.bigroi.stock.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WwwRemover implements Filter{

	@Override
	public void destroy() {
		// do nothing on destroy
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		if (request instanceof HttpServletRequest){
			String url = ((HttpServletRequest)request).getContextPath();
			if (url.contains("://www.")){
				url = url.replace("www.", "");
				((HttpServletResponse)response).sendRedirect(url);
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// do nothing on init
	}

}
