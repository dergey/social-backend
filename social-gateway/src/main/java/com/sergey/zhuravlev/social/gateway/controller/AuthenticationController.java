package com.sergey.zhuravlev.social.gateway.controller;

import com.sergey.zhuravlev.social.gateway.dto.LoginDto;
import com.sergey.zhuravlev.social.gateway.dto.LoginResponseDto;
import com.sergey.zhuravlev.social.gateway.security.jwt.JWTFilter;
import com.sergey.zhuravlev.social.gateway.security.jwt.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Tag(name = "Authentication endpoints")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final ReactiveAuthenticationManager authenticationManager;

    @Operation(description = "Authorizes user, issues token")
    @PostMapping("/authenticate")
    public Mono<ResponseEntity<LoginResponseDto>> authorize(@Valid @RequestBody Mono<LoginDto> request) {
        return request
                .flatMap(login ->
                        authenticationManager
                                .authenticate(new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword()))
                                .flatMap(auth -> Mono.fromCallable(() -> tokenProvider.createToken(auth, login.isRememberMe())))
                )
                .map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
                    return new ResponseEntity<>(new LoginResponseDto(jwt), httpHeaders, HttpStatus.OK);
                });
    }

}
