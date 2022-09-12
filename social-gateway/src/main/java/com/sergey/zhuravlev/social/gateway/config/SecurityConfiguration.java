package com.sergey.zhuravlev.social.gateway.config;

import com.sergey.zhuravlev.social.gateway.security.jwt.JWTFilter;
import com.sergey.zhuravlev.social.gateway.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.web.cors.reactive.CorsWebFilter;

import static org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final ReactiveUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;
    private final CorsWebFilter corsWebFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(
                userDetailsService
        );
        authenticationManager.setPasswordEncoder(passwordEncoder());
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        http
                .securityMatcher(new NegatedServerWebExchangeMatcher(new OrServerWebExchangeMatcher(
                        pathMatchers("/app/**", "/_app/**", "/i18n/**", "/img/**", "/content/**", "/swagger-ui/**", "/v3/api-docs/**", "/test/**"),
                        pathMatchers(HttpMethod.OPTIONS, "/**")
                )))
                .csrf()
                .disable()
                .addFilterBefore(corsWebFilter, SecurityWebFiltersOrder.REACTOR_CONTEXT)
                .addFilterAt(new JWTFilter(tokenProvider), SecurityWebFiltersOrder.HTTP_BASIC)
                .authenticationManager(reactiveAuthenticationManager())
                .exceptionHandling()
                .and()
                .headers()
                .referrerPolicy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .permissionsPolicy().policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
                .and()
                .frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.DENY)
                .and()
                .authorizeExchange()
                .pathMatchers("/api/authenticate").permitAll()
                .pathMatchers("/api/register").permitAll()
                .pathMatchers("/api/activate").permitAll()
                .pathMatchers("/api/account/reset-password/init").permitAll()
                .pathMatchers("/api/account/reset-password/finish").permitAll()
                .pathMatchers("/api/**").authenticated();
        // @formatter:on
        return http.build();
    }
}

