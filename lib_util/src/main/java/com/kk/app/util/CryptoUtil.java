package com.kk.app.util;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加解密工具类
 */

public class CryptoUtil {

    private static final String TAG = CryptoUtil.class.getSimpleName();
    private static final Charset CHAR_SET = Charset.forName("UTF-8");
    private static final String AES_CIPHER_MODE = "AES/CBC/PKCS5Padding";

    //转换类算法，base64

    /**
     * Base64编码转换
     *
     * @param data 输入字符串
     * @return 输出字符串
     */
    public static String encodeBase64(String data) {
        if (StringUtil.isEmpty(data)) {
            Log.i(TAG, "编码数据为空。");
            return null;
        }

        byte[] dataBase64 = Base64.encode(data.getBytes(CHAR_SET), Base64.DEFAULT);

        return new String(dataBase64);
    }

    /**
     * Base64解码转换
     *
     * @param data 输入字符串
     * @return 输出字符串
     */
    public static String decodeBase64(String data) {
        if (StringUtil.isEmpty(data)) {
            Log.i(TAG, "解码数据为空。");
            return null;
        }

        byte[] dataBase64 = Base64.decode(data.getBytes(CHAR_SET), Base64.DEFAULT);

        return new String(dataBase64);
    }

    //摘要类算法，MD5,SHA

    /**
     * 生成str对应的MD5串，128位摘要
     *
     * @param str 输入字符串
     * @return 输出字符串
     */
    public static String genMD5Str(String str) {
        if (StringUtil.isEmpty(str)) {
            Log.i(TAG, "加密数据为空。");
            return null;
        }

        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = str.getBytes(CHAR_SET);

        byte[] md5Bytes = md5.digest(byteArray);

        return StringUtil.bytesToHexString(md5Bytes);
    }

    /**
     * 生成str对应的SHA串，默认生成160位摘要
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String genSHAStr(String str) {
        if (StringUtil.isEmpty(str)) {
            Log.i(TAG, "加密数据为空。");
            return null;
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = str.getBytes(CHAR_SET);
        byte[] digest = md.digest(byteArray);

        return StringUtil.bytesToHexString(digest);
    }

    /**
     * 生成str对应的SHA串
     *
     * @param str       字符串
     * @param algorithm （可取SHA,SHA-256,SHA-384,SHA-512）
     * @return 字符串
     */
    public static String genSHAStr(String str, String algorithm) {
        if (StringUtil.isEmpty(str)) {
            Log.i(TAG, "加密数据为空。");
            return null;
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = str.getBytes(CHAR_SET);
        byte[] digest = md.digest(byteArray);

        return StringUtil.bytesToHexString(digest);
    }

    //对称加密类算法，AES

    /**
     * AES加密，加密密钥key
     *
     * @param value 明文
     * @param key   密钥
     * @return 密文
     */
    public static String encryAES(String value, String key) {
        byte[] data = null;
        try {
            data = value.getBytes(CHAR_SET);
        } catch (Exception e) {
            e.printStackTrace();
        }

        data = encryAES(data, key);

        return StringUtil.bytesToHexString(data);
    }

    /**
     * AES加密，加密密钥key
     *
     * @param value 明文byte数组
     * @param key   密钥
     * @return 密文byte数组
     */
    public static byte[] encryAES(byte[] value, String key) {
        try {
            SecretKeySpec secretKeySpec = createKey(key);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_MODE);
            cipher.init(1, secretKeySpec);
            return cipher.doFinal(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成AESkey
     *
     * @param key 密钥
     * @return AES key
     */
    private static SecretKeySpec createKey(String key) {
        byte[] data;
        if (key == null) {
            key = "";
        }
        StringBuilder sb = new StringBuilder(32);
        sb.append(key);
        while (sb.length() < 32) {
            sb.append("0");
        }
        if (sb.length() > 32) {
            sb.setLength(32);
        }
        data = sb.toString().getBytes(CHAR_SET);
        return new SecretKeySpec(data, "AES");
    }

    /**
     * AES解密，解密密钥key
     *
     * @param value 密文byte数组
     * @param key   密钥
     * @return 明文byte数组
     */
    public static byte[] decryAES(byte[] value, String key) {
        try {
            SecretKeySpec secretKeySpec = createKey(key);
            Cipher cipher = Cipher.getInstance(AES_CIPHER_MODE);
            cipher.init(2, secretKeySpec);
            return cipher.doFinal(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
