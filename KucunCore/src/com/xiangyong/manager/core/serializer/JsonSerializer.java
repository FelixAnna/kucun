package com.xiangyong.manager.core.serializer;

import com.alibaba.fastjson.JSON;

public class JsonSerializer<T> implements Serializer<T> {
    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] bytes) {
        return (T)JSON.parse(bytes);
    }
}
