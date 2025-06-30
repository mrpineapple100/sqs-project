package com.sqs.pokesearch.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTestHelper {

    public static String generateTestToken() {
        String secret = "sehrgeheimesjwtgeheimnismitsehrvielzeichen1234567890";
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject("matthias")
                .claim("username", "matthias")
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000 * 60 * 60)) // 1 Stunde gültig
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }
}
