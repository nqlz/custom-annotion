package com.zt.annotion.customannotion.entity;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.zt.annotion.customannotion.annotion.CheckMatch;
import com.zt.annotion.customannotion.annotion.CheckParam;
import com.zt.annotion.customannotion.annotion.SensitiveInfo;
import com.zt.annotion.customannotion.enums.MatchEnum;
import com.zt.annotion.customannotion.enums.SensitiveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public static String getStr(Predicate<? super String> removeFc, Predicate<? super String> containFc) {
        List<String> fieldNames = ReflectionKit.getFieldList(Person.class).stream().map(i -> "\"" + i.getName() + "\"").collect(Collectors.toList());
        if (Objects.nonNull(removeFc)) {
            fieldNames.removeIf(removeFc);
        }
        return String.join(",", containFc == null ? fieldNames : fieldNames.stream().filter(containFc).collect(Collectors.toList()));
    }
}
