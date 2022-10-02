package com.sergey.zhuravlev.social.controller;

import com.sergey.zhuravlev.social.dto.AuthInfoDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Tag(name = "Authentication endpoints")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Oauth2ManagmentController {

    private final InMemoryClientRegistrationRepository repository;

    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri:}")
    private String issuer;

    @Value("${spring.security.oauth2.client.registration.oidc.client-id:}")
    private String clientId;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, @AuthenticationPrincipal(expression = "idToken") OidcIdToken idToken) {
        StringBuilder logoutUrl = new StringBuilder();
        ClientRegistration oidcClientRegistration = this.repository.findByRegistrationId("oidc");

        String issuerUri = oidcClientRegistration.getProviderDetails().getIssuerUri();
        if (issuerUri.contains("auth0.com")) {
            logoutUrl.append(issuerUri.endsWith("/") ? issuerUri + "v2/logout" : issuerUri + "/v2/logout");
        } else {
            logoutUrl.append(oidcClientRegistration.getProviderDetails().getConfigurationMetadata().get("end_session_endpoint").toString());
        }

        String originUrl = request.getHeader(HttpHeaders.ORIGIN);

        if (issuerUri.contains("auth0.com")) {
            logoutUrl.append("?client_id=").append(oidcClientRegistration.getClientId()).append("&returnTo=").append(originUrl);
        } else {
            logoutUrl.append("?id_token_hint=").append(idToken.getTokenValue()).append("&post_logout_redirect_uri=").append(originUrl);
        }

        request.getSession().invalidate();
        return ResponseEntity.ok().body(Map.of("logoutUrl", logoutUrl.toString()));
    }

    @GetMapping("/auth-info")
    public AuthInfoDto getAuthInfo() {
        return new AuthInfoDto(issuer, clientId);
    }

}
