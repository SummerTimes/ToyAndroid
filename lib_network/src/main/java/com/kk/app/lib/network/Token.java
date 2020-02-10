package com.kk.app.lib.network;

import com.google.gson.annotations.SerializedName;

/**
 * @author billy.qi
 * @since 17/8/17 14:00
 */

public class Token {

    /**
     * accessToken : d075c481-e68c-461f-9d95-68a7c831bb03
     * expiresIn : 1295997
     * tokenKey : a521acea-5d67-40e3-93ac-833925f00d0c
     */

    @SerializedName("accessToken")
    private String accessToken;
    @SerializedName("expiresIn")
    private long expiresIn;
    @SerializedName("tokenKey")
    private String tokenKey;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }
}
