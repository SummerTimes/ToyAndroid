package com.kk.app.lib.network.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * description:缓存工具类
 * date: 2017/8/23 13:43
 *
 * @author mlp00
 */
public class SharedPreferenceUtil {

    /**
     * 使用默认模式 Context.Context.MODE_PRIVATE=0 创造的文件仅能被调用的应用存取（或者共用相同user ID的所有应用）
     * 另外两种模式
     * Context.MODE_WORLD_READABLE,Context.MODE_WORLD_WRITEABLE在api里已不建议使用
     */
    private static int MODE_PRIVATE = Context.MODE_PRIVATE;

    /**
     * 将一个String数据存入到缓存中
     *
     * @param spName   缓存的名字
     * @param keyStr   要存入缓存中的key
     * @param valueStr 要存入缓存中的value
     */
    public static void setStringDataIntoSP(Context context, String spName, String keyStr, String valueStr) {
        SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
        sp.edit().putString(keyStr, valueStr).apply();
    }

    /**
     * 获取缓存中的一个String数据
     *
     * @param spName 缓存的名字
     * @param keyStr 已存入缓存中的key
     * @return 缓存中对应参数key的value，如果没有则返回“”
     */
    public static String getStringValueFromSP(Context context, String spName, String keyStr) {
        return getStringValueFromSP(context, spName, keyStr, "");
    }

    /**
     * 获取缓存中的一个String数据
     *
     * @param spName       缓存的名字
     * @param keyStr       已存入缓存中的key
     * @param defaultValue 缓存中对应参数key的默认值
     * @return 缓存中对应参数key的value，如果没有则返回defaultValue
     */
    public static String getStringValueFromSP(Context context, String spName, String keyStr, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
        return sp.getString(keyStr, defaultValue);
    }

    /**
     * 删除缓存中的某个键值对
     *
     * @param spName 缓存的名字
     * @param key    欲删除的缓存中的key值
     */
    public static void deleteValueInSP(Context context, String spName, String key) {
        SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
        if (sp.contains(key)) {
            sp.edit().remove(key).apply();
        }
    }

    /**
     * 删除缓存中的所有值
     *
     * @param spName 缓存的名字
     */
    public static void deleteAllInSP(Context context, String spName) {
        SharedPreferences sp = context.getSharedPreferences(spName, MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
