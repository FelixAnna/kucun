package com.xiangyong.manager.core.client.model;

import java.lang.reflect.Method;

public class ClientApiModel {

    private Object apiObject; // api 对象

    private Method method; // 执行的方法

    public Object getApiObject() {
        return apiObject;
    }

    public void setApiObject(Object apiObject) {
        this.apiObject = apiObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
