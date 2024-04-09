package com.example.scim_cam.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "cuongcao1";

    public static String generateToken(String subject) {
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTime = 600000; // 10 minutes in milliseconds

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + expirationTime))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true; // Nếu không có lỗi, token hợp lệ
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        } catch (Exception e) {
            System.out.println("JWT Token is invalid");
        }
        return false; // Trong trường hợp có lỗi, token không hợp lệ hoặc đã hết hạn
    }

    public static String getUsernameFromToken(String token, String secretKey) {
        return getClaimFromToken(token, Claims::getSubject, secretKey);
    }

    public static Date getExpirationDateFromToken(String token, String secretKey) {
        return getClaimFromToken(token, Claims::getExpiration, secretKey);
    }

    public static Boolean isTokenExpired(String token, String secretKey) {
        final Date expiration = getExpirationDateFromToken(token, secretKey);
        return expiration.before(new Date());
    }

    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, String secretKey) {
        final Claims claims = getAllClaimsFromToken(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
}
