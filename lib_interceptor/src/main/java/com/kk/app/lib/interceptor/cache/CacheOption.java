package com.kk.app.lib.interceptor.cache;

/**
 * 缓存配置选项
 *
 * @author mlp00
 */
public class CacheOption {
    public static final String CACHE_TYPE_DISK = "disk";
    public static final String CACHE_TYPE_MEMORY = "memory";

    /**
     * 失效时间，单位：秒
     */
    public long expire;
    /**
     * 缓存类型
     */
    public String cacheType;

    public CacheOption(String cacheType, long expire) {
        this.expire = expire;
        this.cacheType = cacheType;
    }
}
