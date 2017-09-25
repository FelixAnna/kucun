package com.xiangyong.manager.core.serializer;

public class SerializerFactory {

    public static <T> Serializer<T> getSerializer(String serializeType){
        if ("kryo".equals(serializeType)) {
            return new KryoSerializer<T>();
        } else if ("jdk".equals(serializeType)) {
            return new JdkSerializer<T>();
        } else if ("string".equals(serializeType)) {
            return new StringSerializer<T>();
        } else if ("json".equals(serializeType)) {
            return new JsonSerializer<T>();
        } else {
            throw new IllegalArgumentException("不支持的序列化方式" + serializeType);
        }
    }
}
