package com.common.jutils.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.common.jutils.exception.AESException;
import com.common.jutils.utils.AESUtil;

import java.io.IOException;

/**
 * @Description 自定义序列化器，用于对数据进行Base64编码后AES加密，并序列化为JSON
 * @Author wyx
 * @Date 2024/12/6
 **/
public class EncryptDataSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (value == null) {
            jsonGenerator.writeNull();
        } else {
            try {
                String encryptedValue = AESUtil.encrypt(value);
                jsonGenerator.writeString(encryptedValue);
            } catch (Exception e) {
                throw new AESException("加密失败", e);
            }
        }
    }
}
