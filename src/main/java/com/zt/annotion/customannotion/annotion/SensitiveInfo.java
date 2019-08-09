package com.zt.annotion.customannotion.annotion;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zt.annotion.customannotion.enums.SensitiveType;
import com.zt.annotion.customannotion.entity.SensitiveInfoSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 功能描述:脱敏注解类
 *
 * @author: MR.zt
 * @date: 2019/7/31 17:20
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerialize.class)
public @interface SensitiveInfo {
    public SensitiveType value();
}
