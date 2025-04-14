package com.common.jutils.enums;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description 消息类
 * @Author wyx
 * @Date 2024/12/5
 **/
public class Message {
    private String grade;
    private String message;
    public static final Map<Integer, String> GRADE_MAP = new HashMap();

    public Message() {
    }

    public Message(String grade, String message) {
        this.grade = grade;
        this.message = message;
    }

    public static Message info(String message) {
        return new Message("info", message);
    }

    public static Message warning(String message) {
        return new Message("warning", message);
    }

    public static Message error(String message) {
        return new Message("error", message);
    }

    public static Message success(String message) {
        return new Message("success", message);
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    static {
        GRADE_MAP.put(200, "success");
        GRADE_MAP.put(303, "info");
        GRADE_MAP.put(400, "error");
        GRADE_MAP.put(401, "error");
        GRADE_MAP.put(402, "warning");
        GRADE_MAP.put(403, "error");
        GRADE_MAP.put(404, "error");
        GRADE_MAP.put(405, "error");
        GRADE_MAP.put(406, "error");
        GRADE_MAP.put(407, "error");
        GRADE_MAP.put(410, "error");
        GRADE_MAP.put(411, "warning");
        GRADE_MAP.put(412, "warning");
        GRADE_MAP.put(413, "warning");
        GRADE_MAP.put(420, "error");
        GRADE_MAP.put(440, "error");
        GRADE_MAP.put(441, "error");
        GRADE_MAP.put(500, "error");
    }
}
