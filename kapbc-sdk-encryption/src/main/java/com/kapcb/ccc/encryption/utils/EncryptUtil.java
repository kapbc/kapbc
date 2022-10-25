package com.kapcb.ccc.encryption.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <a>Title: EncryptUtil </a>
 * <a>Author: kapcb <a>
 * <a>Description: EncryptUtil <a>
 *
 * @author Kapcb
 * @version 1.0
 * @date 2022/10/25 22:27
 * @since 1.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptUtil {

//    private static final String ALGORITHM = "AES";
//
//    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
//
//    public static String encrypt(String key, String source) {
//        if (key == null || source == null) {
//            return null;
//        }
//
//        Key secretKey = getSecretKey(key);
//
//        try {
//
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
//            byte[] result = cipher.doFinal(bytes);
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            return base64Encoder.encode(result);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    public static String decrypt(String key, String content) {
//        if (key == null || content == null) {
//            return null;
//        }
//        Key secretKey = getSecretKey(key);
//        try {
//            Cipher cipher = Cipher.getInstance(ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            BASE64Decoder base64Decoder = new BASE64Decoder();
//            byte[] bytes = base64Decoder.decodeBuffer(content);
//            byte[] result = cipher.doFinal(bytes);
//            return new String(result, StandardCharsets.UTF_8);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    private static Key getSecretKey(String key) {
//        if (key == null) {
//            throw new NullPointerException("key can not be null!");
//        }
//
//        try {
//            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);
//            secureRandom.setSeed(key.getBytes());
//            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
//            keyGenerator.init(secureRandom);
//            return keyGenerator.generateKey();
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("get secret key error, error message : " + e.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        String key = "wuqian@springboot";
//        String content = "1234567";
//
//        String encrypt = EncryptUtil.encrypt(key, content);
//        System.out.println("encrypt = " + encrypt);
//
//        String decrypt = EncryptUtil.decrypt(key, encrypt);
//        System.out.println("decrypt = " + decrypt);
//    }




    private static final String CHARSET = "utf-8";
    private static final String ALGORITHM = "AES";
    private static final String RANDOM_ALGORITHM = "SHA1PRNG";

    public static String aesEncrypt(String content, String key) {

        if (content == null || key == null) {
            return null;
        }
        Key secretKey = getKey(key);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] p = content.getBytes(CHARSET);
            byte[] result = cipher.doFinal(p);
            BASE64Encoder encoder = new BASE64Encoder();
            String encoded = encoder.encode(result);
            return encoded;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String aesDecrypt(String content, String key) {
        Key secretKey = getKey(key);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] c = decoder.decodeBuffer(content);
            byte[] result = cipher.doFinal(c);
            String plainText = new String(result, CHARSET);
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Key getKey(String key) {
        if (key == null) {
            throw  new NullPointerException("key不能为null");
        }

        try {
            SecureRandom secureRandom = SecureRandom.getInstance(RANDOM_ALGORITHM);
            secureRandom.setSeed(key.getBytes());
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            generator.init(secureRandom);
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //对于密码 123456加密
        String encryptPassword = EncryptUtil.aesEncrypt("123456", "wuqian@springboot");
        //结果：NH6/q8SNo929e+bQ4g8dCA==
        System.out.println(encryptPassword);
        //对于newUserName进行解密
        String originPassword = EncryptUtil.aesDecrypt(encryptPassword, "wuqian@springboot");
        //结果：123456
        System.out.println(originPassword);
    }
}
