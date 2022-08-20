package com.sergey.zhuravlev.social.service.storage.exception;

public class StorageFileNotFoundException extends RuntimeException {

    public StorageFileNotFoundException() {
    }

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
