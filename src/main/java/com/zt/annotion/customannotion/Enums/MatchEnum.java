package com.zt.annotion.customannotion.Enums;

import cn.hutool.core.lang.Validator;
import com.zt.annotion.customannotion.entity.CheckIdCard;
import com.zt.annotion.customannotion.entity.ResultJson;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/7/31 12:44
 */
@Getter
public enum MatchEnum {

    /**
     * 身份证校验
     */
    Identity{
        @Override
        public Boolean match(Object obj) {
            return Validator.isCitizenId(obj.toString());
        }
    },
    /**
     * 邮箱校验
     */
    Email{
        @Override
        public Boolean match(Object obj) {
            return Validator.isEmail(obj.toString());
        }
    },
    /**
     * 手机号校验
     */
    Mobile{
        @Override
        public Boolean match(Object obj) {
            return Validator.isMobile(obj.toString());
        }
    },
    /**
     * 自定义正则校验
     */
    MatchRegex {
        @Override
        public Boolean match(Object obj) {
            Map o=(Map)obj;
            return Validator.isMactchRegex(o.get("regex").toString(),o.get("value").toString());
        }
    }
    ;
    public abstract Boolean match(Object obj);

}