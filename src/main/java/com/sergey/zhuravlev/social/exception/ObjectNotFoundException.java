package com.sergey.zhuravlev.social.exception;

import lombok.Getter;

@Getter
public class ObjectNotFoundException extends RuntimeException {

    private final String id;
    private final String objectType;

    public ObjectNotFoundException(String id, String objectType) {
        super(String.format("Object \"%s\" not found by id %s", objectType, id));
        this.id = id;
        this.objectType = objectType;
    }

}
