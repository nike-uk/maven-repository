package com.common.jutils.parser;

import com.common.jutils.exception.AESException;
import com.common.jutils.utils.AESUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @Description 自定义反序列化器，用于解密 JSON 数据。
 * 反序列化时，先使用 AES 解密，再进行 Base64 解码，返回原始字符串。
 * @Author wyx
 * @Date 2024/12/6
 **/
public class DecryptDataDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String encryptedValue = jsonParser.getValueAsString();
        if (encryptedValue == null) {
            return null;
        }
        try {
            return AESUtil.decrypt(encryptedValue);
        } catch (Exception e) {
            throw new AESException("解密失败: ");
        }
    }
}
