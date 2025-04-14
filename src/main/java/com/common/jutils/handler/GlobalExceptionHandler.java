package com.common.jutils.handler;

import com.common.jutils.enums.HttpCodeEnum;
import com.common.jutils.pojo.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

/**
 * @Description 全局异常处理器
 * @Author wyx
 * @Date 2024/12/6
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseResult<String> exceptionHandler(Exception e) {
        logger.error("未知异常！",e);
        return ResponseResult.errorResult(HttpCodeEnum.INTERNAL_SERVER_ERROR,"服务器内部错误！");
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult<String> bindExceptionHandler(BindException e) {

        StringBuilder stringBuilder = new StringBuilder();
        e.getAllErrors().forEach(objectError -> stringBuilder.append(objectError.getDefaultMessage()).append(";"));
        logger.warn("参数校验异常！{}",stringBuilder);
        return ResponseResult.errorResult(HttpCodeEnum.PARAMETER_ERROR,stringBuilder.toString());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult<String> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        logger.warn("请求格式错误 解析失败 ", e);
        if (Objects.requireNonNull(e.getMessage()).contains("Required request body is missing")) {
            return ResponseResult.errorResult(HttpCodeEnum.PARAMETER_ERROR, "请求体不能为空！");
        }
        return ResponseResult.errorResult(HttpCodeEnum.PARAMETER_ERROR, "参数格式错误！");
    }



    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseResult<String> sqlIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e) {
        logger.warn("重复插入，数据库约束异常！",e);
        return ResponseResult.errorResult(HttpCodeEnum.PARAMETER_ERROR,"重复插入");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<String> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        logger.warn("请求方法错误！",e);
        return ResponseResult.errorResult(HttpCodeEnum.PARAMETER_ERROR,"请求方法错误！");
    }

}
