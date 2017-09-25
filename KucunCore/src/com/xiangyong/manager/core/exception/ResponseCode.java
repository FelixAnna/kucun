package com.xiangyong.manager.core.exception;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class ResponseCode implements Serializable {

    // 成功
    public static final ResponseCode SUCCESS = new ResponseCode(200, "成功");
    // 系统异常
    public static final ResponseCode SYSTEM_ERROR = new ResponseCode(500, "系统异常");
    // 参数不合法
    public static final ResponseCode INVALID_PARAM = new ResponseCode(100002, "参数不合法");
    // 超时
    public static final ResponseCode TIME_OUT = new ResponseCode(100003, "系统内部访问超时");
    // json 字符串不合法
    public static final ResponseCode INVALID_JSON_STRING = new ResponseCode(100004, "不合法的json串");
    // 资源存在
    public static final ResponseCode RESOURCE_EXISTS = new ResponseCode(100005, "资源已经存在");
    // 资源不存在
    public static final ResponseCode RESOURCE_NOT_EXISTS = new ResponseCode(100006, "资源不存在");
    // 不支持的操作
    public static final ResponseCode NON_SUPPORTED_OPER = new ResponseCode(100007, "不支持的操作");
    // 无权操作
    public static final ResponseCode NO_PERMISSION = new ResponseCode(100008, "没有权限操作");
    //会话过期
    public static final ResponseCode SESSION_EXPIRED = new ResponseCode(100009, "会话已过期，请重新登陆");

    public static final ResponseCode TOKEN_NOT_EXIST = new ResponseCode(100010, "请求头中token不存在");

    public static final ResponseCode TOKEN_EXPIRE = new ResponseCode(100011, "token已过期");

    public static final ResponseCode REQUEST_METHOD_NOT_SUPPORTED = new ResponseCode(100012, "不支持的请求方式");

    public static final ResponseCode MEDIA_TYPE_NOT_SUPPORTED = new ResponseCode(100013, "不支持的数据类型");

    public static final ResponseCode NOT_SUPPORTED_SOURCE = new ResponseCode(100014, "无效的请求来源");

    private int code;

    private String msg;

    private Object data;

    public static ResponseCode buildResponse(Object data) {
        ResponseCode responseCode = new ResponseCode(SUCCESS.getCode(), "请求成功");
        responseCode.setData(data);
        return responseCode;
    }

    public ResponseCode() {}

    public ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseCode(ResponseCode responseCode, String msg) {
        this.code = responseCode.getCode();
        this.msg = msg;
    }

    public boolean isSuccess(){
        return code == SUCCESS.getCode();
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }

}