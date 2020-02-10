package com.kk.app.lib.interceptor.cache;

/**
 * 缓存接口
 *
 * @author mlp00
 */
public interface ICache {

    /**
     * 保存数据
     *
     * @param key    key
     * @param value  value
     * @param expire 失效时间
     * @return 结果
     */
    boolean save(String key, String value, long expire);

    /**
     * 由key取value
     *
     * @param key kye
     * @return value
     */
    String value(String key);

    /**
     * 由key取缓存块
     *
     * @param key key
     * @return 缓存块
     */
    CacheBlock cache(String key);

    /**
     * 清楚所有数据
     */
    void removeAllData();
}
