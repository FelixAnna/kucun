package com.xiangyong.manager.core.client.exception;

public class ClientApiException extends RuntimeException {

    private static final long serialVersionUID = -8681548786719469363L;

    public ClientApiException(String message) {
        super(message);
    }

    public ClientApiException(String message, Exception e) {
        super(message, e);
    }
}
