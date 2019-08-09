package com.zt.annotion.customannotion.validate;

import com.alibaba.fastjson.JSONObject;
import com.zt.annotion.customannotion.enums.CodeEnum;
import com.zt.annotion.customannotion.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Optional;

/**
 * @author cover 杨乙伟
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
        boolean notConform = object instanceof ServletRequest||object instanceof ServletResponse
                ||object instanceof MultipartFile||object instanceof String
                ||object instanceof Number;
        return !notConform;
    }

    /**
     * 校验参数是JSONObject对象
     */
    public static final void validateStringParam(JSONObject jsonObject, String[] validateAttribute) {
        for (String attribute : validateAttribute) {
            Optional.ofNullable(jsonObject.get(attribute))
                    .map(item->{
                        boolean b = item instanceof String && StringUtils.isBlank(item.toString());
                        if(b){
                            throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, attribute + "属性不能为空字符串!");
                        }
                        return item;
                    })
                    .orElseThrow(()-> new BusinessException(CodeEnum.PARAMS_IS_INVALID, attribute + "属性不能为空!"));
        }
    }
}
