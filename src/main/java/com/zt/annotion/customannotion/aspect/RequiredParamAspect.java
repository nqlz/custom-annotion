package com.zt.annotion.customannotion.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zt.annotion.customannotion.Enums.CodeEnum;
import com.zt.annotion.customannotion.annotion.CheckMatch;
import com.zt.annotion.customannotion.annotion.CheckParam;
import com.zt.annotion.customannotion.annotion.ValidateServiceData;
import com.zt.annotion.customannotion.exception.BusinessException;
import com.zt.annotion.customannotion.validate.CommonJoinPointOperation;
import com.zt.annotion.customannotion.validate.CommonValidate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 功能描述:AOP切面校验类
 *
 * @author: cover 杨乙伟
 * @date: 2019/7/30 9:55
 */
@Aspect
@Component
@Slf4j
public class RequiredParamAspect {


    public RequiredParamAspect() {
        log.info("初始化AOP切面校验类");
    }

    @Pointcut("@annotation(com.zt.annotion.customannotion.annotion.ValidateServiceData)")
    public void controllerRequired() {
    }

    @Before("controllerRequired()")
    public void before(JoinPoint joinPoint) throws Throwable {
        //获取被注解的方法
        Method method = CommonJoinPointOperation.getMethod(joinPoint);
        //获取方法上的注解
        ValidateServiceData validate = method.getAnnotation(ValidateServiceData.class);

        //获取参数对象
        Object[] params = joinPoint.getArgs();
        //获取参数名称
        String[] argsName = CommonJoinPointOperation.getArgsName(method);

        //放行
        if (!validate.openCheckParam()) {
            return;
        } else {
            log.info("方法上存在ValidateServiceData注解.....执行方法参数校验....");
            validateCheckParam(method, argsName, params);
            return;
        }

    }

    //@Around("controllerRequired()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //如果没有报错，放行
        return pjp.proceed();
    }

    /**
     * 校验存在CheckParam注解的参数
     *
     * @param method
     */
    private void validateCheckParam(Method method, String[] argsName, Object[] params) {
        Annotation[][] annotations = method.getParameterAnnotations();
        //只对参数加了相应注解的参数校验，未加注解参数若是普通对象，即使成员变量有注解也不参加校验
        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                Annotation annotation = annotations[i][j];
                Object object = params[i];
                if (annotation instanceof CheckParam) {
                    CheckParam checkParam = (CheckParam) annotation;
                    if (object == null) {
                        throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "参数:" + argsName[i] + "不能为空对象!!!");
                    }
                    //字符串
                    if (object instanceof String) {
                        checkString(object.toString(), argsName[i]);
                    }
                    //基本数据类型以及包装数据类
                    else if (object instanceof Number) {
                        checkNumber((Number) object, argsName[i]);
                    }
                    //集合
                    else if (object instanceof Collection) {
                        checkCollection((Collection) object, argsName[i], checkParam.attributes());
                    }
                    //Map
                    else if (object instanceof Map) {
                        checkMap((Map) object, argsName[i], checkParam.attributes());
                    }
                    //普通对象
                    else if (object instanceof Object) {
                        checkObject(object, argsName[i], checkParam.attributes());
                    }
                } else if (annotation instanceof CheckMatch) {
                    CheckMatch check = (CheckMatch) annotation;
                    doCheckMatch(check, object, argsName[i]);
                }
            }
        }
    }

    /**
     * 正则参数校验
     *
     * @param check
     * @param object
     * @param argName
     */
    private void doCheckMatch(CheckMatch check, Object object, String argName) {
        Object o = Optional
                .ofNullable(object)
                .orElseThrow(() -> new BusinessException(CodeEnum.PARAMS_IS_INVALID, "正则参数:" + argName + "不能为空!!!"));

        if (!check.expression().match(o)) {
            throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "正则参数:" + argName + "格式不合法!!!");
        }
    }

    /**
     * 校验String
     *
     * @param string  字符串
     * @param argName 参数名
     */
    private void checkString(String string, String argName) {
        if (StringUtils.isBlank(string)) {
            throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "String参数:" + argName + "不能为空字符串!!!");
        }
    }

    /**
     * 校验基本数据类型
     *
     * @param number  基本数据
     * @param argName 参数名
     */
    private void checkNumber(Number number, String argName) {
        //啥也不干 也可以校验是否为0
    }

    /**
     * 校验集合类中对象属性
     *
     * @param collection 集合
     * @param argName    参数名
     * @param attributes 校验的属性
     */
    private void checkCollection(Collection collection, String argName, String[] attributes) {
        JSONArray jsonArray = (JSONArray) JSONArray.toJSON(collection);
        if (jsonArray.size() == 0) {
            throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "Collection参数:" + argName + "不能为空集合!!!");
        }
        //不存在属性判断 只需要判定空不空 如List<String>
        if (attributes.length == 0) {
            return;
        }
        for (int x = 0; x < jsonArray.size(); x++) {

            if (!CommonValidate.checkObject2JSON(jsonArray.get(x))) {
                continue;
            }

            JSONObject item;
            try {
                item = jsonArray.getJSONObject(x);
            } catch (Exception e) {
                log.warn("存在某些无法序列化的参数....跳过校验");
                continue;
            }
            //校验参数项
            for (String attribute : attributes) {
                if (item.get(attribute) == null) {
                    throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "Collection参数:" + argName + "的对象属性" + attribute + "不能为空");
                } else if (item.get(attribute) instanceof String) {
                    //字符串
                    if (StringUtils.isBlank(item.getString(attribute))) {
                        throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "Collection参数:" + argName + "的对象属性" + attribute + "不能为空字符串");
                    }
                }
            }
        }
    }

    /**
     * 校验Map对象
     *
     * @param map        Map对象
     * @param argName    参数名
     * @param attributes 校验的参数名
     */
    private void checkMap(Map map, String argName, String[] attributes) {

        if (attributes.length == 0) {
            return;
        }
        // 校验map的key是不是这几个属性
        for (String attribute : attributes) {
            if (map.get(attribute) == null) {
                throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "Map参数" + argName + "的key:" + attribute + "不能为空");
            }
        }

    }

    /**
     * 校验Object对象
     * 对象里字段属性只简单校验null和字符串
     *
     * @param object
     * @param argName
     * @param attributes
     */
    private void checkObject(Object object, String argName, String[] attributes) {
        //如果attributes上有属性，默认不校验成员变量上的注解成员
        if (attributes.length == 0) {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                CheckParam param = field.getAnnotation(CheckParam.class);
                CheckMatch match = field.getAnnotation(CheckMatch.class);
                field.setAccessible(true);
                Object o = null;
                try {
                    o = field.get(object);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //成员变量有注解，先校验注解,成员变量只校验字符串，和基本数据类型
                String fieldName = field.getName();
                if (param != null) {
                    boolean str = field.getType().equals(String.class);
                    boolean num = field.getType().equals(Number.class);
                    if (str) {
                        checkString(o.toString(), fieldName);
                    }
                    if (num) {
                        checkNumber(Integer.valueOf(o.toString()), fieldName);
                    }
                }
                if (match != null) {
                    doCheckMatch(match, o, fieldName);
                }
            }
            return;
        }
        if (!CommonValidate.checkObject2JSON(object)) {
            return;
        }

        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) JSONObject.toJSON(object);
        } catch (Exception e) {
            log.warn("存在某些无法序列化的参数....跳过校验");
            return;
        }

        for (String attribute : attributes) {
            //对象中不存在该属性
            if (jsonObject.get(attribute) == null) {
                throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "Object参数:" + argName + "的属性" + attribute + "不能为空");
            } else if (jsonObject.get(attribute) instanceof String) {
                if (StringUtils.isBlank(jsonObject.getString(attribute))) {
                    throw new BusinessException(CodeEnum.PARAMS_IS_INVALID, "Object参数:" + argName + "的属性" + attribute + "不能为空字符串");
                }
            }
        }
    }
}
