package com.sergey.zhuravlev.social.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PasswordResetStatus {

    EMAIL_CONFIRMATION(false),
    PHONE_CONFIRMATION(false),
    PASSWORD_AWAIT(false),
    FAIL(true);

    private final boolean isFinal;

}
