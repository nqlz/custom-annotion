package com.zt.annotion.customannotion.enums;


import lombok.Getter;

/**
 * 系统错误码
 * @Author kzh
 * @Description
 * @Date 19-5-5 下午5:43
 */
@Getter
public enum CodeEnum {

    // 参数错误状态码
    PARAMS_IS_NULL(10001, "参数不能为空"),
    PARAMS_NOT_COMPLETE(10002, "参数不全"),
    PARAMS_TYPE_ERROR(10003, "参数类型错误"),
    PARAMS_IS_INVALID(10004, "参数无效"),

    // 用户错误状态码
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_NOT_LOGIN(20002, "用户未登录"),
    USER_ACCOUNT_ERROR(20003, "用户名或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20004, "用户账户已经被禁用"),
    USER_ALREADY_EXIST(20005, "用户已经存在"),
    USER_TYPE_ERROR(20006, "用户类型错误"),

    // 业务错误
    BUSINESS_ERROR(30001, "系统业务错误"),

    // 消息发送失败
    MESSAGE_SEND_EROOR(40001, "消息发送失败"),

    // 数据错误
    DATA_NOT_FOUND(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据错误"),
    DATA_ALREADY_EXIST(50003, "数据已经存在"),
    DATA_IS_REFERENCED(50004, "数据被引用中"),
    REPEAT_OPERATOR(50005, "重复操作"),
    OSS_UPLOAD_FAIL_EXCEPTION(50006, "上传失败"),

    // 接口请求错误
    INTERFACE_REQUEST_TIMEOUT(60001, "接口请求超时"),

    // 数据库错误
    DATASOURCE_CHANGE_ERROR(70001, "数据库切换错误"),
    DATABASE_ERROR(70002, "数据库错误"),

    // redisson错误
    REDISSON_LOCK_ERROR(80001, "redisson锁错误"),
    REDISSON_LOCK_TIMEOUTE(80002, "redisson锁等待超时"),

    COURSE_NOT_FOUND(90001, "课程未找到"),
    COURSE_OFF_SALE(90002, "课程已下架"),
    COURSE_ALREADY_BUY(90003, "课程已经购买过"),
    COURSE_TIME_TABLE_NOT_FOUND(90004, "为找到指定期数的课程表"),
    COURSE_BUY_ERROR(90005, "购买课程失败"),
    ;

    private Integer code;

    private String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }}
