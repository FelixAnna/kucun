package com.xiangyong.manager.core.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Rex.Lei on 2017/8/30.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RUNTIME)
@Inherited
public @interface ClientApi {

    String [] value() default "";
}
