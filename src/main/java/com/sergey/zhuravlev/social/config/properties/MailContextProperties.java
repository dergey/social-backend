package com.sergey.zhuravlev.social.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("social.mail")
public class MailContextProperties {

    private String frontendHost;
    private String frontendRegistrationLinkPath;
    private String frontendRegistrationLinkCodeParameter;

}
