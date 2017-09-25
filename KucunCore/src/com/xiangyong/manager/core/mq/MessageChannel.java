package com.xiangyong.manager.core.mq;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface MessageChannel {
	
	String value();

}
