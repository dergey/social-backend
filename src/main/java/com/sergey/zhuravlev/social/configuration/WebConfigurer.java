package com.sergey.zhuravlev.social.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.ServletContext;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfigurer implements ServletContextInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        //todo is this needed?
    }

    @Bean
    public CorsFilter corsFilter() {
        //TODO Allow necessary cors rules
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization,Link,X-Total-Count");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(1800L);
        source.registerCorsConfiguration("/api/**", configuration);
        source.registerCorsConfiguration("/v3/api-docs", configuration);
        source.registerCorsConfiguration("/swagger-ui/**", configuration);
        return new CorsFilter(source);
    }

}
