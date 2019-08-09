package com.zt.annotion.customannotion.aspect;

import cn.hutool.core.util.IdUtil;
import com.zt.annotion.customannotion.enums.CodeEnum;
import com.zt.annotion.customannotion.annotion.RepeatSubmitLimiter;
import com.zt.annotion.customannotion.exception.BusinessException;
import com.zt.annotion.customannotion.validate.CommonJoinPointOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author cover yangyiwei
 * 防止重复提交
 * 处理逻辑 ： 前端先获取一次请求ID 再访问该接口
 */
@Aspect
@Component
@Slf4j
public class RepeatSubmitLimiterAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 拦截所有Controller
     */
    @Pointcut("@annotation(com.zt.annotion.customannotion.annotion.RepeatSubmitLimiter)")
    public void rlAop() {
    }

    @Before("rlAop()")
    public void doBefore(JoinPoint proceedingJoinPoint) {
        HttpServletRequest request = getRequest();
        //可以约定request.getHeader("reqId")
        Method method = CommonJoinPointOperation.getMethod(proceedingJoinPoint);
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String remoteAddr = request.getRemoteAddr();
        Object arg = proceedingJoinPoint.getArgs()[0];
        String reqId = className + "_" + methodName + "_" + remoteAddr + "_" + arg.toString();
        int hashCode = Math.abs(reqId.hashCode());
        if (StringUtils.isEmpty(reqId)) {
            log.warn("请不要重复提交请求....!!!");
            throw new BusinessException(CodeEnum.REPEAT_OPERATOR, "请不要重复提交!");
        }
        RepeatSubmitLimiter limiter = method.getAnnotation(RepeatSubmitLimiter.class);
        // 获取对应的reqId,如果能够获取该reqId，就直接执行具体的业务逻辑
        boolean isFind = delReqId(hashCode, limiter);
        // 获取对应的reqId,如果获取不到该reqId 直接返回请勿重复提交
        if (!isFind) {
            log.warn("请不要重复提交请求....!!!");
            throw new BusinessException(CodeEnum.REPEAT_OPERATOR, "请不要重复提交!");
        }
    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }


    public boolean delReqId(Integer reqIdKey, RepeatSubmitLimiter limiter) {
        //第二次请求 不做处理
        String key = String.valueOf(reqIdKey);
        long timeout = limiter.timeout();
        if (timeout < 0) {
            timeout = 3000;
        }
        String value = (String) redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(value)) {
            return false;
        }
        redisTemplate.opsForValue().set(key, IdUtil.randomUUID(), timeout, limiter.timeUnit());
        return true;
    }

}
