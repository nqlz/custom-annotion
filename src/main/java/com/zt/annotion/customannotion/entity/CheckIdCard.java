package com.zt.annotion.customannotion.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: zoutao
 * @Date: 2018/11/21 10:47
 * @Description:身份证号校验工具类
 */
public class CheckIdCard {
    public static final Boolean checkIdCard(String idCard) {
        // 检测身份证是否包含中文
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher aMatcher = pattern.matcher(idCard);
        boolean isZH = aMatcher.find();

        // 检测身份证位数是否是正常是法定位数（15，17，18）
        // 定义判别用户身份证号的正则表达式（15位或者18位，最后一位可以为字母）
        String regularExpression = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" + "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        boolean matches = idCard.matches(regularExpression);
        boolean amount = (idCard.length() == 15 || idCard.length() == 17 || idCard.length() == 18);

        //判断第18位校验值
        boolean ca18 = true;
        if (matches) {
            if (idCard.length() == 18) {
                try {
                    char[] charArray = idCard.toCharArray();
                    //前十七位加权因子
                    int[] idCardWi = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
                    //这是除以11后，可能产生的11位余数对应的验证码
                    String[] idCardY = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
                    int sum = 0;
                    for (int i = 0; i < idCardWi.length; i++) {
                        int current = Integer.parseInt(String.valueOf(charArray[i]));
                        int count = current * idCardWi[i];
                        sum += count;
                    }
                    char idCardLast = charArray[17];
                    int idCardMod = sum % 11;
                    if (idCardY[idCardMod].toUpperCase().equals(String.valueOf(idCardLast).toUpperCase()))
                    {
                        ca18 =true;
                    } else {
                        System.out.println("身份证最后一位:" + String.valueOf(idCardLast).toUpperCase() + "错误,正确的应该是:" + idCardY[idCardMod].toUpperCase());
                        ca18 = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("异常:" + idCard);
                    ca18 = false;
                }
            }}
            // 条件都满足时，返回true
            boolean existed = (isZH == false) && (amount == true) && (ca18==true);

            return existed;
        }

    }
