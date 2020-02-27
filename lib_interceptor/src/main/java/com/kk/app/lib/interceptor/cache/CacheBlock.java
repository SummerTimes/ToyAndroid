package com.kk.app.lib.interceptor.cache;

import java.io.Serializable;

/**
 * @author kk
 * @datetime: 2019/3/10
 * @desc:缓存块
 */
public class CacheBlock implements Serializable {

    private static final long serialVersionUID = 8598435989810128154L;

    private String value;
    private long expire;

    public CacheBlock(String value, long expire) {
        this.value = value;
        this.expire = expire == 0 ? 0 : expire * 1000 + System.currentTimeMillis();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public boolean isExpire() {
        return expire != 0 && System.currentTimeMillis() - expire >= 0;
    }
}
