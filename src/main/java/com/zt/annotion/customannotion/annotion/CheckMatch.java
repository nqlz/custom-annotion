package com.zt.annotion.customannotion.annotion;

import com.zt.annotion.customannotion.Enums.MatchEnum;

import java.lang.annotation.*;

/**
 * 功能描述:正则表达式校验
 *
 * @author: MR.zt
 * @date: 2019/7/31 14:30
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
public @interface CheckMatch {
    /**
     * 是否开启正则校验
     * @return
     */
    boolean isMatch() default true;

    /**
     * 正则校验方式
     * @return
     */
    MatchEnum expression() default MatchEnum.identity;
}
