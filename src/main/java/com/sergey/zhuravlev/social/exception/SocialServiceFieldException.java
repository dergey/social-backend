package com.sergey.zhuravlev.social.exception;

import com.sergey.zhuravlev.social.enums.ErrorCode;
import lombok.Getter;

@Getter
public class SocialServiceFieldException extends SocialServiceException {

    private final String field;

    public SocialServiceFieldException(String field, ErrorCode code) {
        super(code);
        this.field = field;
    }

    public SocialServiceFieldException(String field, ErrorCode code, String message) {
        super(code, message);
        this.field = field;
    }

}
