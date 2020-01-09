package com.zt.annotion.customannotion.handler;


import com.zt.annotion.customannotion.entity.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;

import static com.zt.annotion.customannotion.entity.ResultJson.returnError;


/**
 * @Author kzh
 * @Description 异常处理类
 * @Date 19-3-1 下午5:52
 */
@RestControllerAdvice
public class SysExceptionHandler {
    @Autowired
    HttpServletRequest request;
    private static final Logger log = LoggerFactory.getLogger(SysExceptionHandler.class);

    /**
     * SQLException异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResultJson sqlExceptionHandler(SQLException e) {
        e.printStackTrace();
        return returnError(e.getMessage(), "请求错误");
    }

    /**
     * BadSqlGrammarException异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResultJson badSqlGrammarExceptionHandler(BadSqlGrammarException e) {
        e.printStackTrace();
        return returnError(e.getMessage(), "请求错误");
    }

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResultJson bindException(BindException ex) {
        List<ObjectError> allErrors = ex.getAllErrors();//捕获的所有错误对象
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (Integer i = 0; i < allErrors.size(); i++) {
            if (i != allErrors.size() - 1) {
                builder.append(allErrors.get(i).getDefaultMessage() + ",");
            } else {
                builder.append(allErrors.get(i).getDefaultMessage());
            }
        }
        builder.append("]");
        ex.printStackTrace();
        return returnError("", builder.toString());

    }

    /**
     * 处理请求对象属性不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultJson exception(HttpServletRequest request, MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage() + "\n");
        }
        exception.printStackTrace();
        return returnError("", builder.toString());
    }

    /**
     * 处理请求单个参数不满足校验规则的异常信息
     *
     * @param request
     * @param exception
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultJson constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
        exception.printStackTrace();
        return returnError("", exception.getMessage());
    }


    /**
     * 顶级异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResultJson exceptionHandler(Exception e) {
        e.printStackTrace();
        return returnError("", e.getMessage());
    }

}
