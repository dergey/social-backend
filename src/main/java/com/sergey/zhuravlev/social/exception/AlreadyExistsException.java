package com.sergey.zhuravlev.social.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsException extends RuntimeException {

    private final String id;
    private final String objectType;

    public AlreadyExistsException(String id, String objectType) {
        super(String.format("Object \"%s\" already exist with id %s", objectType, id));
        this.id = id;
        this.objectType = objectType;
    }

}
