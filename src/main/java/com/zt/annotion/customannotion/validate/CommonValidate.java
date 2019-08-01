package com.zt.annotion.customannotion.validate;

import com.alibaba.fastjson.JSONObject;
import com.zt.annotion.customannotion.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Description: 通用校验逻辑封装
 */
public class CommonValidate {

    /**
     * 判断Object能否序列化
     *
     * @param object
     * @return
     */
    public static final boolean checkObject2JSON(Object object) {
        if (object instanceof ServletRequest) {
            return false;
        }
        if (object instanceof ServletResponse) {
            return false;
        }
        if (object instanceof MultipartFile) {
            return false;
        }
        if (object instanceof String) {
            return false;
        }
        if (object instanceof Number) {
            return false;
        }
        return true;
    }

    /**
     * 校验参数是JSONObject对象
     */
    public static final void validateStringParam(JSONObject jsonObject, String[] validateAttribute) {
        for (String attribute : validateAttribute) {
            if (jsonObject.get(attribute) == null) {
                throw new BusinessException(10086, attribute + "属性不能为空!");
            } else if (jsonObject.get(attribute) instanceof String) {
                if (StringUtils.isBlank(jsonObject.getString(attribute))) {
                    throw new BusinessException(10086, attribute + "属性不能为空字符串!");
                }
            }
        }
    }
}
