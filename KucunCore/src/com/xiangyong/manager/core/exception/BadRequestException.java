package com.xiangyong.manager.core.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private String message;
    private int code;

    public BadRequestException(int code, String msg){
        this.message =msg;
        this.code=code;
    }

    public BadRequestException(String msg){
        this.message =msg;
        this.code= HttpStatus.BAD_REQUEST.value();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
