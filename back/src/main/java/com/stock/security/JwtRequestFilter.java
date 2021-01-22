package com.stock.security;

import com.stock.entity.business.UserRecord;
import com.stock.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final UserService userService;
    private final JwtTokenProcessor jwtTokenProcessor;

    public JwtRequestFilter(UserService userService, JwtTokenProcessor jwtTokenProcessor) {
        super();
        this.jwtTokenProcessor = jwtTokenProcessor;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain
    ) throws ServletException, IOException {
        var requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (requestTokenHeader == null) {
            requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER.toLowerCase());
        }
        String username = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenProcessor.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                this.userService.getByUsername(username)
                        .filter(u -> jwtTokenProcessor.validateAccessToken(jwtToken, u.getUserName()))
                        .ifPresent(u -> grandAccess(request, u));
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        chain.doFilter(request, response);
    }

    private void grandAccess(HttpServletRequest request, UserRecord user) {
        var grantedAuthorities = user.getRoles()
                .stream()
                .map(Role::getSpringName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        var authenticationToken = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
