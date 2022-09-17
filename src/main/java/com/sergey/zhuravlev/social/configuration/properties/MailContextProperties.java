package com.sergey.zhuravlev.social.configuration.properties;

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
    private String frontendPasswordResetLinkPath;
    private String frontendPasswordResetLinkCodeParameter;

}
