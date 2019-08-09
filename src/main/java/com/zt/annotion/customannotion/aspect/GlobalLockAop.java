package com.zt.annotion.customannotion.aspect;

import cn.hutool.core.collection.CollUtil;
import com.zt.annotion.customannotion.enums.CodeEnum;
import com.zt.annotion.customannotion.annotion.GlobalLock;
import com.zt.annotion.customannotion.exception.BusinessException;
import com.zt.annotion.customannotion.validate.CommonJoinPointOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 分布式锁 切面
 */
@Aspect
@Slf4j
public class GlobalLockAop {


    @Autowired
    @Qualifier("redissonClient")
    private RedissonClient redisson;

    /**
     * 定义切入点
     */
    @Pointcut("@annotation(com.zt.annotion.customannotion.annotion.GlobalLock)")
    public void globalLockPoint() {

    }

    @Around("globalLockPoint()")
    public Object invokeMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = CommonJoinPointOperation.getMethod(joinPoint);
        GlobalLock distributedLock = method.getAnnotation(GlobalLock.class);
        //解析 锁定的key
        String lockPath = parseLockPath(joinPoint, method, distributedLock);

        RLock lock = redisson.getLock(lockPath);
        if (lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit())) {
            log.info("成功获取分布式锁...锁定的key:{}", lockPath);
            try {
                return joinPoint.proceed();
            } finally { //方法执行完后 释放锁
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    log.info("释放分布式锁...释放的key:{}", lockPath);
                }
            }
        } else {
            throw new BusinessException(CodeEnum.DATA_IS_REFERENCED, "有用户正在操作当前资源 请稍后再试");
        }

    }

    /**
     * 返回需要锁定的 key
     *
     * @param joinPoint
     * @param method
     * @param distributedLock
     * @return
     */
    private String parseLockPath(ProceedingJoinPoint joinPoint, Method method, GlobalLock distributedLock) {
        String lockPath = distributedLock.path();
        // 如果未填写
        if (StringUtils.isBlank(lockPath)) {
            lockPath = joinPoint.getTarget().getClass().getSimpleName() + "_" + method.getName();
        }

        if (ArrayUtils.isNotEmpty(distributedLock.key())) {
            String key = parseKey(distributedLock.key(), method, joinPoint.getArgs());
            lockPath = lockPath + "_" + key;
        }

        return lockPath;
    }

    /**
     * 通过spring 解析Eval 表达式
     *
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String parseKey(String[] key, Method method, Object[] args) {
        if (ArrayUtils.isEmpty(key)) {
            throw new IllegalArgumentException("锁路径(lockPath)不能为空");
        }

        String[] paraNameArr = CommonJoinPointOperation.getArgsName(method);
        List<String> strings = new ArrayList<>();
        SpelExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        for (int i = 0; i < key.length; i++) {
            String value = parser.parseExpression(key[i]).getValue(context, String.class);
            strings.add(value);
        }
        String reStr = CollUtil.join(strings, ":");
        return reStr;
    }


}
