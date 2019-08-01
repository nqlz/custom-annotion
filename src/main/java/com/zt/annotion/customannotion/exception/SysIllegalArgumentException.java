package com.zt.annotion.customannotion.exception;

import com.zt.annotion.customannotion.Enums.CodeEnum;
import lombok.Data;

/**
 * 自定义参数异常
 * @Author kzh
 * @Description
 * @Date 19-5-7 上午9:33
 */
@Data
public class SysIllegalArgumentException extends RuntimeException {
    private Integer code;

    private String message;

    public SysIllegalArgumentException(CodeEnum codeEnum, String message) {
        this(codeEnum.getCode(), message);
    }

    public SysIllegalArgumentException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
