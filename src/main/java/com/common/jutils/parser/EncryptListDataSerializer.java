package com.common.jutils.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.common.jutils.exception.AESException;
import com.common.jutils.utils.AESUtil;

import java.io.IOException;
import java.util.List;

/**
 * @Description 自定义序列化器，用于对List<String>数据进行Base64编码后AES加密，并序列化为JSON
 * @Author wyx
 * @Date 2024/12/6
 **/
public class EncryptListDataSerializer  extends JsonSerializer<List<String>> {

    @Override
    public void serialize(List<String> strings, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        try {
            for (String s : strings) {
                String encrypted = AESUtil.encrypt(s);
                jsonGenerator.writeString(encrypted);
            }
        } catch (Exception e) {
            throw new AESException("加密失败", e);
        }
    }
}
