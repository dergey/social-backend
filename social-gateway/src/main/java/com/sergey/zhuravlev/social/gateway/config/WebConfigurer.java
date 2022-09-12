package com.sergey.zhuravlev.social.gateway.config;

import com.sergey.zhuravlev.social.gateway.config.properties.SocialWebProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements WebFluxConfigurer {

    private final SocialWebProperties webProperties;

    @Bean
    public CorsWebFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = webProperties.getCors();
        if (!CollectionUtils.isEmpty(config.getAllowedOrigins()) || !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/management/**", config);
            source.registerCorsConfiguration("/v3/api-docs", config);
            source.registerCorsConfiguration("/swagger-ui/**", config);
            source.registerCorsConfiguration("/*/api/**", config);
            source.registerCorsConfiguration("/services/*/api/**", config);
            source.registerCorsConfiguration("/*/management/**", config);
        }
        return new CorsWebFilter(source);
    }

}
