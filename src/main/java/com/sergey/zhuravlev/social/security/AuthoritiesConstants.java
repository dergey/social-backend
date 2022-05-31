package com.sergey.zhuravlev.social.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum AuthoritiesConstants {

//    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS");

    private final String role;

    @Override
    public String toString() {
        return role;
    }

}
