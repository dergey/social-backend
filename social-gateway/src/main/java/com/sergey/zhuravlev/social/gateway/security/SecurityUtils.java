package com.sergey.zhuravlev.social.gateway.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Mono<String> getCurrentUserLogin() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .flatMap(authentication -> Mono.justOrEmpty(extractPrincipal(authentication)));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static Mono<String> getCurrentUserJWT() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    public static Mono<Boolean> isAuthenticated() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getPrincipal)
            .map(Objects::nonNull);
    }

    public static Mono<Boolean> hasCurrentUserAnyOfAuthorities(String... authorities) {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .map(Authentication::getAuthorities)
            .map(authorityList ->
                authorityList
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(authority -> Arrays.asList(authorities).contains(authority))
            );
    }

    public static Mono<Boolean> hasCurrentUserNoneOfAuthorities(String... authorities) {
        return hasCurrentUserAnyOfAuthorities(authorities).map(result -> !result);
    }

    public static Mono<Boolean> hasCurrentUserThisAuthority(String authority) {
        return hasCurrentUserAnyOfAuthorities(authority);
    }
}
