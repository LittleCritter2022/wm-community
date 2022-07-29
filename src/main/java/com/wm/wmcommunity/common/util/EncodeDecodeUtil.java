package com.wm.wmcommunity.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * -------------------------------------------------------------------------------
 * Copyright  2022 BeiJing HongTianXinYe Technologies Co.,Ltd. All rights reserved.
 * -------------------------------------------------------------------------------
 * Module Name: wm-community >>> EncodeDecodeUtil
 * Product:
 * Creator: Gerry(0120)
 * Date Created: 2022/7/20
 * Description:
 * -------------------------------------------------------------------------------
 * Modification History
 * DATE                       Name                  Description
 * -------------------------------------------------------------------------------
 * 2022/7/20                 Gerry(0120)                 Create
 * -------------------------------------------------------------------------------
 *
 * @author Gerry(0120)
 */
@Slf4j
public class EncodeDecodeUtil {
    private static final Base64.Decoder BASE64_DECODER = Base64.getDecoder();
    private static final Base64.Encoder BASE64_ENCODER = Base64.getEncoder();

    /**
     * 构造方法
     */
    private EncodeDecodeUtil() {
    }

    /**
     * Base64 加密
     *
     * @param bytes 待加密字符串bytes
     * @return 加密后的字符串
     */
    public static String base64Encode(byte[] bytes) {
        return BASE64_ENCODER.encodeToString(bytes);
    }

    /**
     * Base64 加密
     *
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String base64Encode(String str) {
        return BASE64_ENCODER.encodeToString(str.getBytes());
    }

    /**
     * Base64 解密
     *
     * @param str 待解密字符串
     * @return 解密后的字符串
     */
    public static String base64Decode(String str) {
        return new String(BASE64_DECODER.decode(str));
    }

    /**
     * Base64 解密
     *
     * @param str 待解密字符串
     * @return 解密后的字符串bytes
     */
    public static byte[] base64DecodeToBytes(String str) {
        return BASE64_DECODER.decode(str);
    }

    /**
     * RSA加密
     *
     * @param str       待加密字符串
     * @param publicKey 公钥
     * @return 加密后的字符串
     */
    public static String rsaEncode(String str, String publicKey) {
        try {
            byte[] keyBytes = base64DecodeToBytes(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey pubKey = fact.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            byte[] encrypted = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
            return base64Encode(encrypted);
        } catch (Exception e) {
            log.error("Aes encode failed", e);
            return null;
        }
    }

    /**
     * RSA解密
     *
     * @param str        待解密字符串
     * @param privateKey 私钥
     * @return 解密后的字符串
     */
    public static String rsaDecode(String str, String privateKey) {
        try {
            byte[] keyBytes = base64DecodeToBytes(privateKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PrivateKey priKey = fact.generatePrivate(keySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priKey);

            str = str.replaceAll(" +", "+");
            byte[] encrypted = base64DecodeToBytes(str);
            byte[] original = cipher.doFinal(encrypted);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Aes decode failed", e);
            return null;
        }
    }
}
