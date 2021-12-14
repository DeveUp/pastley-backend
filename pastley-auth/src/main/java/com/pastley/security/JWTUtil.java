package com.pastley.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @project Pastley-Auth.
 * @author Sergio Stives Barrios Buitrago.
 * @Github https://github.com/SerBuitrago.
 * @contributors leynerjoseoa.
 * @version 1.0.0.
 */
@Component
public class JWTUtil{
	
	private static final String KEY = "UFPS";

    Set<String> jwtActive = new HashSet<>();

    public String generateToken(UserDetails userDetails) {
        String jwt = Jwts.builder().setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
        jwtActive.add(jwt);
        return jwt;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(extractUsername(token)) && !isTokenValid(token);
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public void removeToken(String token) {
        jwtActive.remove(token);
    }

    public boolean isTokenValid(String token) {
        return jwtActive.contains(token);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
