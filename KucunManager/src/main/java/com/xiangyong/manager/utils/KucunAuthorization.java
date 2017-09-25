package com.xiangyong.manager.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在 Controller 或 方法上使用此注解，该方法在映射时会检查用户是否登录，未登录返回 401 错误
 * @author ScienJus
 * @date 2015/7/31.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface KucunAuthorization {
}
