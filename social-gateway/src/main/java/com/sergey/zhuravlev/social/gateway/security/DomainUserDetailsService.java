package com.sergey.zhuravlev.social.gateway.security;

import com.sergey.zhuravlev.social.gateway.provider.UserProvider;
import com.sergey.zhuravlev.social.gateway.validation.EmailOrPhoneValidator;
import com.sergey.zhuravlev.social.model.user.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component("userDetailsService")
public class DomainUserDetailsService implements ReactiveUserDetailsService {

    private final UserProvider userProvider;

    public DomainUserDetailsService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public Mono<UserDetails> findByUsername(final String login) {
        if (EmailOrPhoneValidator.isEmail(login)) {
            return userProvider
                    .getUserByEmail(login)
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException("User with email " + login + " was not found")))
                    .map(user -> createSpringSecurityUser(login, user));
        } else if (EmailOrPhoneValidator.isPhone(login)) {
            return userProvider
                    .getUserByPhone(Mono.just(login))
                    .switchIfEmpty(Mono.error(new UsernameNotFoundException("User with phone " + login + " was not found")))
                    .map(user -> createSpringSecurityUser(login, user));
        } else {
            return Mono.error(new UsernameNotFoundException("User login is not contain email or phone"));
        }
    }

    private User createSpringSecurityUser(String principal, UserModel user) {
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), null, Collections.emptyList());
    }

}
