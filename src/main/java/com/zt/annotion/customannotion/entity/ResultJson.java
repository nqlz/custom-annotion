package com.zt.annotion.customannotion.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zt.annotion.customannotion.enums.CodeEnum;
import lombok.Data;

import java.util.Optional;

/**
 * @Author kzh
 * @Description
 * @Date 19-5-5 下午5:58
 */
@Data
public class ResultJson {
    public static final Integer SUCCESS = 1;
    public static final Integer FAIL = 0;

    private Integer code;

    private Integer status;

    private String message;

    private Object data;

    public static ResultJson returnError(CodeEnum codeEnum) {
        return returnError(codeEnum.getCode(), codeEnum.getMessage(), null);
    }

    /**
     * 封装失败响应信息
     * @param codeEnum
     * @param message
     * @return
     */
    public static ResultJson returnError(CodeEnum codeEnum, String message) {
        return returnError(codeEnum.getCode(), message, null);
    }

    public static ResultJson returnError(CodeEnum codeEnum, String message, Object data) {
        return returnError(codeEnum.getCode(), message, data);
    }

    public static ResultJson returnError(Integer code, String message) {
        return returnError(code, message, null);
    }
    /**
     * 封装失败响应信息
     * @param code
     * @param message
     * @return
     */
    public static ResultJson returnError(Integer code,String message, Object data) {
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(FAIL);
        resultJson.setData(data);
        resultJson.setCode(code);
        resultJson.setMessage(message);
        return resultJson;
    }


    /**
     * 封装成功响应信息
     * @return
     */
    public static ResultJson returnOK() {
        return returnOK(null, null);
    }

    /**
     * 封装成功响应信息
     * @param message
     * @return
     */
    public static ResultJson returnOK(String message) {
        return returnOK(null, message);
    }

    /**
     * 封装成功响应信息
     * @param data
     * @return
     */
    public static ResultJson returnOK(Object data) {
        return returnOK(data, "数据请求成功");
    }

    /**
     * 封装成功响应信息
     * @param data
     * @param message
     * @return
     */
    public static ResultJson returnOK(Object data, String message) {
        ResultJson resultJson = new ResultJson();
        resultJson.setStatus(SUCCESS);
        resultJson.setData(data);
        resultJson.setMessage(message);
        return resultJson;
    }

    public <T> T extractData(Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(this.getData()), clazz);
    }

    public <T> Optional<T> extractData(String fieldName, Class<T> clazz) {
        JSONObject jsonObject = this.data2JsonObject();
        return Optional.ofNullable(jsonObject.getObject(fieldName, clazz));
    }

    public JSONObject data2JsonObject() {
        return JSON.parseObject(JSON.toJSONString(this.getData()));
    }
}
