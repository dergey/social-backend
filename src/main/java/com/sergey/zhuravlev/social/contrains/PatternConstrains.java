package com.sergey.zhuravlev.social.contrains;

import java.util.regex.Pattern;

public final class PatternConstrains {

    public final static String EMAIL_OR_PHONE_PATTERN_VALUE = "(\\d{7,15})|(\\w+@\\w+\\.\\w{2,3})";
    public final static Pattern EMAIL_OR_PHONE_PATTERN = Pattern.compile(EMAIL_OR_PHONE_PATTERN_VALUE);

}
