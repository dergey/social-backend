package com.sergey.zhuravlev.social.exception;

import lombok.Getter;

@Getter
public class FieldAlreadyExistsException extends RuntimeException {

    private final String attribute;
    private final String attributeValue;

    public FieldAlreadyExistsException(String message, String attribute, String attributeValue) {
        super(message);
        this.attribute = attribute;
        this.attributeValue = attributeValue;
    }

}
