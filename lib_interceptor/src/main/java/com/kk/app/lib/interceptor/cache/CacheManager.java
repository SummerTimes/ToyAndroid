package com.kk.app.lib.interceptor.cache;

import android.content.Context;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author kk
 * @datetime: 2019/3/10
 * @desc:缓存管理类
 */
public class CacheManager {

    private ICache memCache;
    private ICache diskCache;

    private static class CacheManagerHolder {
        private static final CacheManager INSTANCE = new CacheManager();
    }

    public static CacheManager getInstance() {
        return CacheManagerHolder.INSTANCE;
    }

    public void setup(Context context, int version) {
        CacheManagerHolder.INSTANCE.memCache = new MemoryCache();
        CacheManagerHolder.INSTANCE.diskCache = new DiskCache(version, getDiskCacheDir(context, "BLCache"));
    }

    private String keyForRequest(String url, String requestData) {
        String cacheKey;
        String key = url + requestData;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public CacheBlock retrieveData(String url, String requestData, String storage) {
        if (memCache != null && CacheOption.CACHE_TYPE_MEMORY.equals(storage)) {
            return memCache.cache(keyForRequest(url, requestData));

        } else if (diskCache != null && CacheOption.CACHE_TYPE_DISK.equals(storage)) {
            return diskCache.cache(keyForRequest(url, requestData));
        }

        return null;
    }

    public boolean cacheData(String url, String requestData, String raw, String storage, long expire) {
        if (memCache != null && CacheOption.CACHE_TYPE_MEMORY.equals(storage)) {
            return memCache.save(keyForRequest(url, requestData), raw, expire);

        } else if (diskCache != null && CacheOption.CACHE_TYPE_DISK.equals(storage)) {
            return diskCache.save(keyForRequest(url, requestData), raw, expire);
        }
        return false;
    }

    public void removeMemData() {
        if (memCache != null) {
            memCache.removeAllData();
        }
    }

    public void removeDiskData() {
        if (diskCache != null) {
            diskCache.removeAllData();
        }
    }

    public void removeAllData() {
        removeMemData();
        removeDiskData();
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
