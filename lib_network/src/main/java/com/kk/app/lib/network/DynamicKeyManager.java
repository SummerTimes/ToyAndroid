package com.kk.app.lib.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kk.app.lib.network.util.BmpUtilRefactoring;
import com.kk.app.lib.network.util.CryptoUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author kk
 * @datetime: 2020/4/24
 * @desc:动态密钥管理
 */
public class DynamicKeyManager {

    public static final String KEY_VERSION = "2";
    private static String dynamicKey;
    private static final String TAG = "DynamicKeyManager";

    public static void initDynamicKey(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("XXX.bmp");
            dynamicKey = BmpUtilRefactoring.readStringFromInputStream(inputStream);
            Log.d(TAG, "dynamicKey:" + dynamicKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 秘钥选择流程：
     * a)         获取当前时间戳(注:java方法:long time = System.currentTimeMillis();)
     * b)         使用前端MD5加密该时间戳产生16进制的字符串
     * c)         取该字符串的最后两位得到新的字符串
     * d)         使用新的字符串转化成int类型取模100，即为秘钥的下标index
     * e)         获取秘钥，秘钥串是800个字符组成的字符串，通过 keys.substring(index*8,index*8+8);获得
     * f)          使用上步获取的秘钥进行des加解密。
     */
    public static String getEncryptKey(String time) {
        String key = "";
        if (TextUtils.isEmpty(dynamicKey)) {
            return key;
        }

        String timeMD5 = CryptoUtil.genMD5Str(time);
        if (!TextUtils.isEmpty(timeMD5) && timeMD5.length() > 2) {
            int timeMD5Len = timeMD5.length();
            int index = Integer.parseInt(timeMD5.substring(timeMD5Len - 2, timeMD5Len), 16) % 100;
            key = dynamicKey.substring(index * 8, index * 8 + 8);
        }

        return key;
    }
}
