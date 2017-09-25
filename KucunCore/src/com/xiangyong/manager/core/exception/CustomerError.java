package com.xiangyong.manager.core.exception;

import com.xiangyong.manager.common.util.DataUtils;
import org.springframework.http.HttpStatus;

public class CustomerError extends RuntimeException {
    private String message;
    private int code;

    public CustomerError(HttpStatus status){
        this.code=status.value();
        this.message=status.getReasonPhrase();
    }

    public CustomerError(HttpStatus status, String message){
        this.code=status.value();
        this.message=message;
    }

    public CustomerError(int code, String message){
        this.code=code;
        this.message=message;
    }

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
