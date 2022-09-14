package com.sergey.zhuravlev.social.mail;

import com.sergey.zhuravlev.social.config.properties.MailContextProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MailFrontendContext {

    private final MailContextProperties properties;

    public String getFrontendRegistrationConfirmationLink(String code) {
        return UriComponentsBuilder.fromHttpUrl(this.properties.getFrontendHost()).path(this.properties.getFrontendRegistrationLinkPath()).queryParamIfPresent(this.properties.getFrontendRegistrationLinkCodeParameter(), Optional.ofNullable(code)).toUriString();
    }

}
