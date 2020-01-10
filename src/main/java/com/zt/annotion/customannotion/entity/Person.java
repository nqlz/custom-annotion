package com.zt.annotion.customannotion.entity;

import com.zt.annotion.customannotion.annotion.CheckMatch;
import com.zt.annotion.customannotion.annotion.CheckParam;
import com.zt.annotion.customannotion.annotion.SensitiveInfo;
import com.zt.annotion.customannotion.enums.MatchEnum;
import com.zt.annotion.customannotion.enums.SensitiveType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
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
        List<String> objects = new ArrayList<>();
        ReflectionUtils.doWithFields(Person.class, i -> objects.add("\"" + i.getName() + "\""));
        objects.removeIf(removeFc != null ? removeFc : i -> i.contains("Date"));
        List<String> relList = containFc == null ? objects : objects.stream().filter(containFc).collect(Collectors.toList());
        return String.join(",", relList);
    }
}
