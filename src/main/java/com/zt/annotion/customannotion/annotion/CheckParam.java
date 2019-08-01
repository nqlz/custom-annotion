package com.zt.annotion.customannotion.annotion;

import java.lang.annotation.*;

/**
 * 功能描述:校验参数
 *
 * @author: cover 杨乙伟
 * @date: 2019/7/30 9:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
public @interface CheckParam {

    /**
     * 要校验的参数 属性 如果是基本类型 无需填写
     * @return
     */
    String[] attributes() default {};

}
