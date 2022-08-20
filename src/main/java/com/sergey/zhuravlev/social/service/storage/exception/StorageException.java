package com.sergey.zhuravlev.social.service.storage.exception;

public class StorageException extends RuntimeException {

    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
