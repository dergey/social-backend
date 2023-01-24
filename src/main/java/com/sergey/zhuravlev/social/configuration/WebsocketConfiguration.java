package com.sergey.zhuravlev.social.configuration;

import com.sergey.zhuravlev.social.configuration.properties.CorsProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {

    private final CorsProperties corsProperties;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        String[] allowedOriginPatterns = Optional
            .ofNullable(corsProperties.getAllowedOriginPatterns())
            .map(origins -> origins.toArray(new String[0]))
            .orElse(new String[0]);
        registry
                .addEndpoint("/websocket/android-messages")
                .setHandshakeHandler(defaultHandshakeHandler())
                .setAllowedOriginPatterns(allowedOriginPatterns);
        registry
            .addEndpoint("/websocket/messages")
            .setHandshakeHandler(defaultHandshakeHandler())
            .setAllowedOriginPatterns(allowedOriginPatterns)
            .withSockJS();
    }

    private DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                Principal principal = request.getPrincipal();
                if (principal == null) {
                    throw new AccessDeniedException("WS don't support unauthorized requests");
                }
                return principal;
            }
        };
    }
}
