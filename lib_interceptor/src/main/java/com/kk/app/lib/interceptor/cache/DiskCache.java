package com.kk.app.lib.interceptor.cache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author kk
 * @datetime: 2019/3/10
 * @desc:磁盘缓存
 */
public class DiskCache implements ICache {
    private DiskLruCache mDiskLruCache;
    /**
     * 磁盘缓存大小100 M
     */
    private static final long MAX_CACHE_SIZE = 100 * 1024 * 1024;

    private final int versionCode;
    private final File diskCacheDir;


    public DiskCache(int appVersion, File cacheDir) {
        versionCode = appVersion;
        rebuild(cacheDir);
        diskCacheDir = cacheDir;
    }

    private boolean rebuild(File cacheDir) {
        boolean isDirSuccess = true;
        if (!cacheDir.exists()) {
            isDirSuccess = cacheDir.mkdirs();
        }
        // 创建DiskLruCache实例，初始化缓存数据
        try {
            if (isDirSuccess) {
                mDiskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, MAX_CACHE_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public synchronized boolean save(String key, String value, long expire) {
        CacheBlock block = new CacheBlock(value, expire);
        try {
            if (diskCacheDir == null) {
                System.err.print("disk cache not defined");
                return false;
            }
            if (mDiskLruCache.isClosed() && !rebuild(diskCacheDir)) {
                System.err.print("open cache failed");
                return false;
            }

            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                ObjectOutputStream out = new ObjectOutputStream(editor.newOutputStream(0));
                out.writeObject(block);
                out.close();
                editor.commit();
                return true;
            }

        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        }
        return false;
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
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskLruCache.get(key);

            if (snapshot != null) {
                ObjectInputStream input = new ObjectInputStream(snapshot.getInputStream(0));
                CacheBlock block = (CacheBlock) input.readObject();

                if (block != null) {
                    if (block.isExpire()) {
                        mDiskLruCache.remove(key);
                    } else {
                        return block;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (snapshot != null) {
                snapshot.close();
            }
        }

        return null;
    }

    @Override
    public void removeAllData() {
        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
