package com.zt.annotion.customannotion.handler;

import com.alibaba.fastjson.JSON;
import com.zt.annotion.customannotion.enums.CodeEnum;
import com.zt.annotion.customannotion.entity.ResultJson;
import com.zt.annotion.customannotion.exception.BusinessException;
import com.zt.annotion.customannotion.exception.SysIllegalArgumentException;
import com.zt.annotion.customannotion.exception.SysIllegalStateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

import static com.zt.annotion.customannotion.entity.ResultJson.returnError;


/**
 * @Author kzh
 * @Description
 * @Date 19-5-5 下午5:57
 */
@Slf4j
@RestControllerAdvice
public class SysExceptionHandler {

    /**
     * SQLException异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResultJson sqlExceptionHandler(SQLException e) {
        log.error("【异常处理】SQLException异常信息:message:{}", JSON.toJSONString(e));
        return returnError(CodeEnum.BUSINESS_ERROR, "请求错误");
    }

    /**
     * BusinessException异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResultJson businessHandler(BusinessException e) {
        log.error("【异常处理】BusinessException异常信息:message:{}", JSON.toJSONString(e));
        return returnError(e.getErrorCode(), e.getMessage());
    }

    /**
     * 顶级异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultJson exceptionHandler(Exception e) {
        log.error("【异常处理】Exception异常信息:message:{}", JSON.toJSONString(e));
        return returnError(CodeEnum.BUSINESS_ERROR, "请求错误");
    }

    /**
     * 参数异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(SysIllegalArgumentException.class)
    public ResultJson illegalArgumentExceptionHandler(SysIllegalArgumentException e) {
        log.error("【异常处理】SysIllegalArgumentException异常信息:message:{}", JSON.toJSONString(e));
        return returnError(e.getCode(), e.getMessage());
    }

    /**
     * 状态异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(SysIllegalStateException.class)
    public ResultJson illegalStateExceptionHandler(SysIllegalStateException e) {
        log.error("【异常处理】SysIllegalStateException异常信息:message:{}", JSON.toJSONString(e));
        return returnError(e.getCode(), e.getMessage());
    }

}
