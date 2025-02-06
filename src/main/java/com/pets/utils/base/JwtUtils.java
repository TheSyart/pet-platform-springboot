package com.pets.utils.base;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.security.Key;
import java.util.logging.Logger;
@Service
public class JwtUtils {
    private static final Key signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final Long expire = 43200000L; // 12小时
    private static final Logger logger = Logger.getLogger(JwtUtils.class.getName());

    // JWT令牌生成
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        logger.info("生成的JWT: " + jwt);
        return jwt;
    }

    // JWT令牌解析
    public static Claims parseJWT(String jwt) {
        logger.info("解析JWT: " + jwt);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        // 获取标准字段
        Date expiration = claims.getExpiration(); // 获取过期时间

        logger.info("userName: " + claims.get("loginUsername"));
        logger.info("passWord: " + claims.get("loginPassword"));
        logger.info("identity: " + claims.get("loginIdentity"));
        logger.info("Expiration: " + expiration);
        return claims;
    }
}

