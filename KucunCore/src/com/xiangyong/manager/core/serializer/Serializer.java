package com.xiangyong.manager.core.serializer;

public interface Serializer<T> {

	byte[] serialize(T obj) ;
	
	T deserialize(byte[] bytes) ;
}
