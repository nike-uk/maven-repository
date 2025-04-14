package com.common.jutils.exception;

/**
 * @Description 自定义异常类，用于处理 AES 加解密过程中的异常。
 * @Author wyx
 * @Date 2024/12/6
 **/
public class AESException extends RuntimeException {

    /**
     * 默认构造方法
     */
    public AESException() {
        super("AES 加解密异常");
    }

    /**
     * 带错误信息的构造方法
     * @param message 错误信息
     */
    public AESException(String message) {
        super(message);
    }

    /**
     * 带错误信息和嵌套异常的构造方法
     * @param message 错误信息
     * @param cause 嵌套异常
     */
    public AESException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 带嵌套异常的构造方法
     * @param cause 嵌套异常
     */
    public AESException(Throwable cause) {
        super(cause);
    }
}
