package com.common.jutils.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 路径参数打印日志
 * @Author wyx
 * @Date 2024/12/11
 **/
@Aspect
@Component
public class AopLog {

    private static final Logger log = LoggerFactory.getLogger(AopLog.class);

    private static final String START_TIME = "request-start";

    private static final String REQUEST_ID = "request_id";

    /**
     * 切入点
     */
    @Pointcut("execution(public * com..controller.*Controller.*(..))")
    public void log() {
    }
    /**
     * 前置操作
     *
     * @param point 切入点
     */
    @Before("log()")
    public void beforeLog(JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuffer sb = new StringBuffer();
        String requestId = IdUtil.simpleUUID();
        sb.append("\n【request_id】：").append(requestId);
        sb.append("\n【请求 URL】：").append(request.getRequestURL());
        sb.append("\n【请求 IP】：").append(getIp(request));
        sb.append("\n【请求类名】：").append(point.getSignature().getDeclaringTypeName());
        sb.append("【请求方法名】：").append(point.getSignature().getName());
        sb.append("\n【body】：").append(JSONUtil.toJsonStr(point.getArgs()));
        sb.append("\n【请求参数】：").append(JSONUtil.toJsonStr(parameterMap));
        log.info(sb.toString());
        Long start = System.currentTimeMillis();
        request.setAttribute(START_TIME, start);
        request.setAttribute(REQUEST_ID, requestId);
    }

    /**
     * 环绕操作
     *
     * @param point 切入点
     * @return 原方法返回值
     * @throws Throwable 异常信息
     */
    @Around("log()")
    public Object aroundLog(ProceedingJoinPoint point) throws Throwable {
        Object result = point.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String requestId = (String) request.getAttribute(REQUEST_ID);
        StringBuffer sb = new StringBuffer();
        sb.append("\n【request_id】：").append(requestId);
        sb.append("\n【返回值】：").append(JSONUtil.toJsonStr(result));
        log.info(sb.toString());
//        log.info("\n【request_id】：" + requestId + "\n【返回值】：{}", JSONUtil.toJsonStr(result));
        return result;
    }

    /**
     * 后置操作
     */
    @AfterReturning("log()")
    public void afterReturning() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        String requestId = (String) request.getAttribute(REQUEST_ID);
        StringBuffer sb = new StringBuffer();
        sb.append("\n【request_id】：").append(requestId);
        Long start = (Long) request.getAttribute(START_TIME);
        Long end = System.currentTimeMillis();
        sb.append("\n【请求耗时】：").append((end - start)).append("毫秒");

        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(header);
        sb.append("\n【原始User-Agent】：").append(header);
        log.info(sb.toString());
    }

    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
