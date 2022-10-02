package com.sergey.zhuravlev.social.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@ConfigurationProperties("social.cors")
public class CorsProperties extends CorsConfiguration {
}
