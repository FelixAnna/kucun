package com.xiangyong.manager.core.cache.util;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.Assert;

public class Md5KeyGenerator implements KeyGenerator {
    public Object generate(Object target, Method method, Object[] params) {
        Assert.notNull(params, "缓存的方法必须要参数");
        String jsonString = JSON.toJSONString(params);
        return com.xiangyong.manager.core.cache.util.MD5Util.MD5(jsonString);
    }
}