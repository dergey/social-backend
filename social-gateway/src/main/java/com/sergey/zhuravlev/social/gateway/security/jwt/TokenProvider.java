package com.sergey.zhuravlev.social.gateway.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private JwtParser jwtParser;

    @Value("${social.security.authentication.jwt.secret}")
    private String jwtSecret;

    @Value("${social.security.authentication.jwt.base64Secret}")
    private String jwtBase64Secret;

    @Value("${social.security.authentication.jwt.tokenValidityInSeconds}")
    private Long jwtTokenValidityInSeconds;

    @Value("${social.security.authentication.jwt.tokenValidityInSecondsForRememberMe}")
    private Long jwtTokenValidityInSecondsForRememberMe;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes;
        if (!ObjectUtils.isEmpty(jwtBase64Secret)) {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode(jwtBase64Secret);
        } else {
            log.warn(
                    "Warning: the JWT key used is not Base64-encoded. " +
                            "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security."
            );
            keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        }
        key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + 1000 * jwtTokenValidityInSecondsForRememberMe);
        } else {
            validity = new Date(now + 1000 * jwtTokenValidityInSeconds);
        }

        return Jwts
            .builder()
            .setSubject(authentication.getName())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .serializeToJsonWith(new JacksonSerializer<>())
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
            .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
            .filter(auth -> !auth.trim().isEmpty())
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            jwtParser.parseClaimsJws(authToken);

            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException e) {
            log.trace("Invalid JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("Token validation error {}", e.getMessage());
        }

        return false;
    }

}
