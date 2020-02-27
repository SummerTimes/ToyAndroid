package com.kk.app.lib.interceptor.cache;

import android.util.LruCache;

/**
 * @author kk
 * @datetime: 2019/3/10
 * @desc:内存缓存
 */
public class MemoryCache implements ICache {
    /**
     * 缓存大小10 M
     */
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;

    private LruCache<String, CacheBlock> mMemoryCache;

    public MemoryCache() {
        mMemoryCache = new LruCache<>(MAX_CACHE_SIZE);
    }

    @Override
    public synchronized boolean save(String key, String value, long expire) {
        CacheBlock block = new CacheBlock(value, expire);
        mMemoryCache.put(key, block);
        return true;
    }

    @Override
    public String value(String key) {
        CacheBlock block = cache(key);

        if (block != null) {
            return block.getValue();
        }

        return null;
    }

    @Override
    public synchronized CacheBlock cache(String key) {
        CacheBlock block = mMemoryCache.get(key);
        if (block != null && !block.isExpire()) {
            return block;
        }

        return null;
    }

    @Override
    public void removeAllData() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                mMemoryCache.evictAll();
            }

            mMemoryCache = new LruCache<>(MAX_CACHE_SIZE);
        }
    }
}
