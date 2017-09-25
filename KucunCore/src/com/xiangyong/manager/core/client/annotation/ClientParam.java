package com.xiangyong.manager.core.client.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by Rex.Lei on 2017/8/30.
 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
@Inherited
public @interface ClientParam {

    String value() default "";
}
