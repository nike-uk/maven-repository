package com.common.jutils.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.common.jutils.exception.AESException;
import com.common.jutils.utils.AESUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description 自定义反序列化器，用于解密包含加密字符串的 JSON 数组。
 * 反序列化时，将数组中的每个加密字符串依次解密，返回解密后的字符串列表。
 * @Author wyx
 * @Date 2024/12/6
 **/
public class DecryptListDataDeserializer extends JsonDeserializer<List<String>> {

    @Override
    public List<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        List<String> decryptedValues = new ArrayList<>();
        try {
            ArrayNode arrayNode = jsonParser.readValueAsTree();
            Iterator<JsonNode> elements = arrayNode.elements();
            while (elements.hasNext()) {
                JsonNode node = elements.next();
                String encryptedValue = node.asText();
                String decryptedValue = AESUtil.decrypt(encryptedValue);
                decryptedValues.add(decryptedValue);
            }
            return decryptedValues;
        } catch (Exception e) {
            throw new AESException("解密失败: ", e);
        }
    }
}
