package com.zt.annotion.customannotion.Enums;

import com.zt.annotion.customannotion.entity.CheckIdCard;
import com.zt.annotion.customannotion.entity.ResultJson;
import lombok.Data;
import lombok.Getter;

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
    identity{
        @Override
        public Boolean match(Object obj) {
            return CheckIdCard.checkIdCard(obj.toString());
        }
    };
    public abstract Boolean match(Object obj);

}