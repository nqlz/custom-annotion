package com.zt.annotion.customannotion.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.lang3.ClassUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 自定义填充处理器
 *
 * @author lanjerry
 * @date 2018/10/31 11:25
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldVal(metaObject, "updateTime", "createTime","createDate","updateDate");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldVal(metaObject, "updateTime","updateDate");
    }

    private void setFieldVal(MetaObject metaObject, String... fieldName) {
        for (String s : fieldName) {
            if (!metaObject.hasGetter(s)) {
                continue;
            }
            Class<?> getterType = metaObject.getSetterType(s);
            if (ClassUtils.isAssignable(getterType,Date.class)) {
                this.setFieldValByName(s, new Date(), metaObject);
            }
            else if (ClassUtils.isAssignable(getterType,LocalDateTime.class)) {
                this.setFieldValByName(s, LocalDateTime.now(), metaObject);
            }
            else if (ClassUtils.isAssignable(getterType,LocalDate.class)) {
                this.setFieldValByName(s, LocalDate.now(), metaObject);
            }
        }
    }
}
