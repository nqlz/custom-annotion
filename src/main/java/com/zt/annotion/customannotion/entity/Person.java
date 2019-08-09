package com.zt.annotion.customannotion.entity;

import com.zt.annotion.customannotion.enums.MatchEnum;
import com.zt.annotion.customannotion.enums.SensitiveType;
import com.zt.annotion.customannotion.annotion.CheckMatch;
import com.zt.annotion.customannotion.annotion.CheckParam;
import com.zt.annotion.customannotion.annotion.SensitiveInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能描述:
 *
 * @author: MR.zt
 * @date: 2019/7/31 11:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @CheckParam
    private String name;
    @CheckParam
    private Integer age;
    @CheckMatch(matchType = MatchEnum.Identity)
    @SensitiveInfo(SensitiveType.ID_CARD)
    private String idCard;
}
