package com.xiangyong.manager.core.exception;

public class ApplicationException extends RuntimeException{

    private com.xiangyong.manager.core.exception.ResponseCode responseCode;

    public ApplicationException() {
        responseCode = com.xiangyong.manager.core.exception.ResponseCode.buildResponse(null);

    }

    public ApplicationException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.responseCode = responseCode;
    }

    public ApplicationException(ResponseCode responseCode, String message) {
        super(message);
        this.responseCode = new ResponseCode(responseCode.getCode(), message);
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

}
