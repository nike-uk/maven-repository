package com.common.jutils.pojo;

import com.common.jutils.enums.HttpCodeEnum;
import com.common.jutils.enums.Message;

import java.io.Serializable;

/**
 * @Description 用于封装API响应结果，包含状态码、消息和数据。
 * @Author wyx
 * @Date 2024/12/5
 **/
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private Message msg;
    private T data;

    public static <T> ResponseResult<T> responseResult(Integer code, Message msg, T data) {
        ResponseResult<T> tResponseResult = new ResponseResult<>();
        tResponseResult.setCode(code);
        tResponseResult.setMsg(msg);
        tResponseResult.setData(data);
        return tResponseResult;
    }

    public static <T> ResponseResult<T> responseResult(Integer code, Message msg) {
        ResponseResult<T> tResponseResult = new ResponseResult<>();
        tResponseResult.setCode(code);
        tResponseResult.setMsg(msg);
        return tResponseResult;
    }

    public static <T> ResponseResult<T> errorResult(int code, String msg) {
        return responseResult(Integer.valueOf(code), Message.error(msg), null);
    }

    public static <T> ResponseResult<T> okResult() {
        return responseResult(HttpCodeEnum.SUCCESS.getCode(), Message.success(HttpCodeEnum.SUCCESS.getMessage()), null);
    }

    public static <T> ResponseResult<T> okResult(int code, String msg) {
        return responseResult(Integer.valueOf(code), new Message((String) Message.GRADE_MAP.get(Integer.valueOf(code)), msg), null);
    }

    public static <T> ResponseResult<T> okResult(T data) {
        ResponseResult<T> objectResponseResult = responseResult(HttpCodeEnum.SUCCESS.getCode(), Message.success(HttpCodeEnum.SUCCESS.getMessage()));
        if (data != null) {
            objectResponseResult.setData(data);
        }
        return objectResponseResult;
    }

    public static <T> ResponseResult<T> errorResult(HttpCodeEnum enums) {
        return okResult(enums.getCode().intValue(), enums.getMessage());
    }

    public static <T> ResponseResult<T> errorResult(HttpCodeEnum enums, String msg) {
        return okResult(enums.getCode().intValue(), msg);
    }

    public static <T> ResponseResult<T> setHttpCodeEnum(HttpCodeEnum enums) {
        return okResult(enums.getCode().intValue(), enums.getMessage());
    }

    private static <T> ResponseResult<T> setHttpCodeEnum(HttpCodeEnum enums, String msg) {
        return okResult(enums.getCode().intValue(), msg);
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Message getMsg() {
        return this.msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
