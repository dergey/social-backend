package com.sergey.zhuravlev.social.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectNotFoundException extends RuntimeException {

    private final String id;
    private final String objectType;

}
