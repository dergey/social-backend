package com.sergey.zhuravlev.social.gateway.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@ConfigurationProperties("social.web")
public class SocialWebProperties {

    private CorsConfiguration cors;

}
