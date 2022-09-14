package com.sergey.zhuravlev.social.enums;

public enum ErrorCode {
    UNAUTHORIZED,
    NOT_FOUND,
    ALREADY_EXIST,
    ARGUMENT_TYPE_MISMATCH,
    FIELD_ERROR,
    NOT_VALID,
    NOT_IMPLEMENTED,
    UNKNOWN_ERROR,

    // non generic errors:

    // registration:
    CONFIRMATION_HAS_EXPIRED,
    INVALID_NEW_USER_STATE,
    TOO_MANY_CONFIRMATION_TRIES,

    // password reset:
    INVALID_PASSWORD_RESET_STATE,

    // fields error:
    INVALID_EMAIL_OR_PHONE_FORMAT,
    //ALREADY_EXIST,
    IN_USE;

    public static final String NOT_BLANK_CODE = "NOT_BLANK";
    public static final String NOT_NULL_CODE = "NOT_NULL";
    public static final String WRONG_LENGTH_CODE = "WRONG_LENGTH";
    public static final String PAST_REQUIRED_CODE = "PAST_REQUIRED";
    public static final String INVALID_EMAIL_FORMAT_CODE = "INVALID_EMAIL_FORMAT";
    public static final String INVALID_EMAIL_OR_PHONE_FORMAT_CODE = "INVALID_EMAIL_OR_PHONE_FORMAT";

}
