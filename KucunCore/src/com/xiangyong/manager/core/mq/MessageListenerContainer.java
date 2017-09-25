package com.xiangyong.manager.core.mq;

public interface MessageListenerContainer {
	
	/**
	 * 启动监听容器
	 */
	void start();
	
	/**
	 * 关闭监听容器
	 */
	void shutdown();
	
	
}
