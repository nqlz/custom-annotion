package com.zt.annotion.customannotion.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 重复提交注解
 */
@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RepeatSubmitLimiter {

    String value() default "";

    /**
     * 指定时间内不可重复提交,单位毫秒
     * @return
     */
    long timeout()  default 3000 ;

    /**
     * 等待时间 持有时间 默认 以秒为单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
