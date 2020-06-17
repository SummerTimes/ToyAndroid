package com.kk.app.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * description:字符串操作工具类
 * author: mlp00
 * date: 2017/8/23 13:45
 */
public class LuStringUtils {

    // BUFFER大小
    public static int BUFFER_SIZE = 512;
    private static final Pattern pattern = Pattern.compile("[0-9]*");

    /**
     * 字符串非空校验
     *
     * @param str 需校验字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmptyOrNull(String target) {
        return TextUtils.isEmpty(target.trim()) || "null".equalsIgnoreCase(target.trim())
                || "nil".equalsIgnoreCase(target.trim());
    }

    /**
     * 字符串是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 判断是否为空或null 输入参数
     *
     * @param target 入参
     * @return
     */
    public static boolean checkNull(Object target) {
        return target == null || "".equals(target.toString().trim()) || "null".equalsIgnoreCase(target.toString().trim());
    }

    /**
     * 字符串null转化为空
     *
     * @param str 需转化字符串
     * @return
     */
    public static String nullToBlank(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 去掉字符串首尾，中间的空格，trim()，不仅仅是去掉空格，此处主要是增加去掉中间的空格
     *
     * @param str
     * @return
     */
    public static String removeSpace(String str) {

        if (!TextUtils.isEmpty(str)) {
            return str.trim().replaceAll(" ", "");
        } else {
            return str;
        }
    }

    /**
     * 将输入流转化成字符串
     *
     * @param encoding 字符编码类型,如果encoding传入null，则默认使用utf-8编码。
     * @return 字符串
     * @throws IOException
     * @author lvmeng
     */
    public static String inputToString(InputStream inputStream, String encoding)
            throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        inputStream.close();
        bos.close();
        if (TextUtils.isEmpty(encoding)) {
            encoding = "UTF-8";
        }
        return new String(bos.toByteArray(), encoding);
    }

    /**
     * 2进制转换16进制
     *
     * @param bString 2进制串
     * @return hexString 16进制串
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
            return null;
        }
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }

    /**
     * 16进制转换2进制
     *
     * @param hexString 16进制串
     * @return bString 2进制串
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * byte数组转换为十六进制字符串
     *
     * @return HexString 16进制串
     */
    public static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte element : bArray) {
            sTemp = Integer.toHexString(255 & element);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTemp);
        }
        return sb.toString();
    }

    /**
     * char转换为byte
     *
     * @param ch
     * @return
     */
    private static byte charToByte(char ch) {
        return (byte) "0123456789ABCDEF".indexOf(ch);
    }

    public static void log(String tag, String msg) {
        int size = msg.length();
        int end;
        final int cnt = 2048;
        for (int len = 0; len < size; len += cnt) {
            end = size <= (len + cnt) ? size : len + cnt;
            Log.e("log-" + tag, msg.substring(len, end));
        }
    }

}
