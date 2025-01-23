package com.sky.ecommerce.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtProvider {

    private static final long EXPIRATION_TIME = 864000000; // 10 days in milliseconds
    private final SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(Authentication auth) {
        return Jwts.builder()
                .setSubject(auth.getName()) // Set email as subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + EXPIRATION_TIME))
                .claim("email", auth.getName()) // Optional: keep as a claim if needed
                .signWith(key)
                .compact();
    }

    public String getEmailFromJwtToken(String jwt) {
        jwt=jwt.substring(7);

        Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String email=String.valueOf(claims.get("email"));

        return email;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> auths=new HashSet<>();

        for(GrantedAuthority authority:collection) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }
}
