package com.sergey.zhuravlev.social.exception;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import lombok.Getter;

@Getter
public class SocialServiceException extends RuntimeException {

    private final ErrorCode code;

    public SocialServiceException(ErrorCode code) {
        super("SocialServiceException: " + code.name());
        this.code = code;
    }

    public SocialServiceException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

}
