package com.common.jutils.enums;

/**
 * @Description HTTP状态码枚举类
 * @Author wyx
 * @Date 2024/12/5
 **/
public enum HttpCodeEnum {
    SUCCESS(200, "操作成功"),
    ERROR(400, "操作失败"),
    LOGIN_FAIL(401, "登录失败"),
    REGISTER_FAIL(402, "注册失败"),
    NOT_FOUND(404, "未找到"),
    PARAMETER_ERROR(410, "参数错误"),
    PERMISSION_DENIED(403, "没有权限"),
    INTERNAL_SERVER_ERROR(500, "服务器错误");

    private final Integer code;
    private final String message;

    HttpCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
