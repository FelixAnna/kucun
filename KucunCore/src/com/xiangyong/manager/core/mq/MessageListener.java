package com.xiangyong.manager.core.mq;

public interface MessageListener<T extends Message> {
	
	/**
	 * 接收并处理订阅消息
	 * @param message 消息
	 */
	void onMessage(T message);

}

