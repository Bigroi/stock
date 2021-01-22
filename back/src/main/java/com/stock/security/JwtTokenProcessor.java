package com.stock.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public class JwtTokenProcessor {

    private final long accessTokenValidity;
    private final long refreshTokenValidity;
    private final String secret;

    public JwtTokenProcessor(String secret, long accessTokenValidity, long refreshTokenValidity) {
        this.secret = secret;
        this.accessTokenValidity = accessTokenValidity;
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String generateAccessToken(String username) {
        return generateToken(username, new Date(), accessTokenValidity);
    }

    public String generateRefreshToken(String username, Date refreshTokenIssued) {
        return generateToken(username, refreshTokenIssued, refreshTokenValidity);
    }

    public Boolean validateAccessToken(String token, String username) {
        return getUsernameFromToken(token).equals(username)
                && getClaimFromToken(token, Claims::getExpiration).after(new Date());
    }

    public Boolean validateRefreshToken(String token, Date refreshTokenIssued) {
        var truncatedDate = new Date(refreshTokenIssued.toInstant().getEpochSecond() * 1000);
        return getClaimFromToken(token, Claims::getExpiration).after(new Date())
                && getClaimFromToken(token, Claims::getIssuedAt).equals(truncatedDate);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    private String generateToken(String username, Date tokenIssued, long tokenValidity) {
        return Jwts.builder().setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(tokenIssued)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}