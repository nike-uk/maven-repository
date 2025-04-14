package com.common.jutils.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Description AES加密解密工具类，加密时先Base64编码后AES加密，解密时先AES解密后Base64解码
 * @Author wyx
 * @Date 2024/12/5
 **/

public class AESUtil {

    private static final String KEY = "wyxchenrui123456";
    private static final String ALGORITHM = "AES";
    private static final String MODE = "AES/ECB/PKCS5Padding";

    public static String encrypt(String sSrc) throws Exception {
        String base64Encoded = Base64.getEncoder().encodeToString(sSrc.getBytes(StandardCharsets.UTF_8));
        byte[] raw = KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(base64Encoded.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String sSrc) throws Exception {
        byte[] base64Decoded = Base64.getDecoder().decode(sSrc);
        byte[] raw = KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(base64Decoded);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

//    public static void main(String[] args) {
//        try {
//            String originalText = "123456";
//            String encryptedText = AESUtils.encrypt(originalText);
//            System.out.println("Encrypted: " + encryptedText);
//            String decryptedText = AESUtils.decrypt(encryptedText);
//            System.out.println("Decrypted: " + decryptedText);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
