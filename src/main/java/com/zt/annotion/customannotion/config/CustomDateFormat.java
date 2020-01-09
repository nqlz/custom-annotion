package com.zt.annotion.customannotion.config;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.apache.commons.lang3.StringUtils;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018-11-7.
 */
public class CustomDateFormat extends StdDateFormat {
    private static final long serialVersionUID = -3201781773655300201L;

    public static final CustomDateFormat instance = new CustomDateFormat();


    /**
     * @ClassName: CustomDateFormat
     * 这个方法可不写，jckson主要使用的是parse(String)这个方法用来转换日期格式的，
     * 只要覆盖parse(String)这个方法即可
     * @date 2018年01月23日 下午4:28:57
     */
    @Override
    public Date parse(String dateStr, ParsePosition pos) {
        return getSimpleDateFormat(dateStr, pos);
    }

    @Override
    public Date parse(String dateStr) {
        return getSimpleDateFormat(dateStr, new ParsePosition(0));
    }

    public Date getSimpleDateFormat(String dateStr, ParsePosition pos) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (dateStr.length() == 10) {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr, pos);
        }
        if (dateStr.length() == 16) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr, pos);
        }
        if (dateStr.length() == 19) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr, pos);
        }
        if (dateStr.length() == 23) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateStr, pos);
        }
        return super.parse(dateStr, pos);
    }

    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date, toAppendTo, fieldPosition);
    }

    public CustomDateFormat clone() {
        return new CustomDateFormat();
    }

}
