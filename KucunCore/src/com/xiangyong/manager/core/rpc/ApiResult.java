package com.xiangyong.manager.core.rpc;

import com.alibaba.fastjson.JSON;

/**
 * Created by Rex.Lei on 2017/8/31.
 */
public class ApiResult<T> {

    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = -1;

    private int code;

    private String msg;

    private T data;

    private ApiResult(){

    }

    private ApiResult(int code, String msg,T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiResult<T> success(String msg) {
        return new ApiResult<>(SUCCESS_CODE,msg,null);
    }

    public static <T> ApiResult<T> success(String msg,T data) {
        return new ApiResult<>(SUCCESS_CODE,msg,data);
    }

    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(FAIL_CODE,msg,null);
    }

    public static <T> ApiResult<T> fail(String msg,T data) {
        return new ApiResult<>(FAIL_CODE,msg,data);
    }

    public boolean isSuccess(){
        return code == SUCCESS_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
