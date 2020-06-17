/**
 * @Probject Name: phone_service
 * @Path: com.bailian.middleware.utilDES.java
 * @Create By songwei
 * @Create In 2015年1月5日 下午4:28:26
 * TODO
 */
package com.kk.app.lib.interceptor;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author songwei
 * @datetime: 2015/1/5
 * @desc:Name DES
 */
public class DES {

    private static final byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};

    public static String encryptDES(String encryptString, String encryptKey) throws Exception {
        if (TextUtils.isEmpty(encryptString)) {
            return "";
        }
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }

    public static String decryptDES(String decryptString, String decryptKey) throws Exception {
        if (TextUtils.isEmpty(decryptString)) {
            return "";
        }
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

}
