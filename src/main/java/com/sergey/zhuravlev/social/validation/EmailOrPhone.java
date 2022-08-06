package com.sergey.zhuravlev.social.validation;

import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailOrPhone {
    String message() default "NOT_VALID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}