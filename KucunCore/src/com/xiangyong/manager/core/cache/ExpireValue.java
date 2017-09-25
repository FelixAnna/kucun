package com.xiangyong.manager.core.cache;

public interface ExpireValue<T> {

	T getValue();
	
	void setValue(T value, int seconds);
	
	void clear();
}
