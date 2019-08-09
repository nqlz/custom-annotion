package com.zt.annotion.customannotion.exception;

import com.zt.annotion.customannotion.enums.CodeEnum;
import lombok.Data;

/**
 * 自定义状态异常
 * @Author kzh
 * @Description
 * @Date 19-5-7 上午9:36
 */
@Data
public class SysIllegalStateException extends RuntimeException {
    private Integer code;

    private String message;

    public SysIllegalStateException(CodeEnum codeEnum, String message) {
        this(codeEnum.getCode(), message);
    }

    public SysIllegalStateException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
