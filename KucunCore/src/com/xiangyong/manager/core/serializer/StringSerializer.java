package com.xiangyong.manager.core.serializer;

import java.nio.charset.Charset;

@SuppressWarnings("unchecked")
public class StringSerializer<T> implements Serializer<T> {
	
	private Charset charset = Charset.forName("utf-8");
	
	public byte[] serialize(T t)  {
		return t.toString().getBytes(charset);
	}

	public T deserialize(byte[] bytes)  {
		return (T)new String(bytes, charset);
	}
}