package com.kk.app.lib.interceptor;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.Chain;
import com.billy.cc.core.component.ICCInterceptor;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author kk
 * @datetime: 2020/02/13
 * @desc:拦截器Cookie
 */
public class NetworkCookieInterceptor implements ICCInterceptor {

    private static final NetworkCookieInterceptor INSTANCE = new NetworkCookieInterceptor();

    private static final String TAG = "xp";

    private static final String KEY_RESPONSE_HEADERS = "responseHeaders";

    private static final String KEY_URL = "url";

    private static final String KEY_COOKIE = "Cookie";

    private static final String KEY_HEADERS = "headers";

    private static final String KEY_LOGIN_URL = "https://www.wanandroid.com/user/login";
    private List<String> mHeaders;

    private NetworkCookieInterceptor() {
    }

    public static NetworkCookieInterceptor get() {
        return INSTANCE;
    }

    @Override
    public CCResult intercept(Chain chain) {
        CC cc = chain.getCC();
        beforeCall(cc);
        CCResult ccResult = chain.proceed();
        return afterResult(cc, ccResult);
    }

    /**
     * 请求添加cookie到header
     *
     * @param cc
     */
    private void beforeCall(CC cc) {
        JSONObject params = new JSONObject(cc.getParams());
        String url = params.optString(KEY_URL);
        try {
            String headers = params.optString(KEY_HEADERS);
            Log.i(TAG, "-----beforeCall----headers----:" + headers);
            if (!TextUtils.isEmpty(headers) && !TextUtils.equals(KEY_LOGIN_URL, url)) {
                JSONObject header = new JSONObject(headers);
                if (mHeaders != null && mHeaders.size() != 0) {
                    for (int i = 0; i < mHeaders.size(); i++) {
                        String cookie = mHeaders.get(i);
                        Log.i(TAG, "---beforeCall-----cookie------:" + cookie);
                        header.put(KEY_COOKIE + i, cookie);
                    }
                }
                params.put(KEY_HEADERS, header);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, "---beforeCall--新增数据---params------:" + params);
    }

    /**
     * 过滤登录接口/获取cookie/保存
     *
     * @param cc
     * @param ccResult
     * @return
     */
    @SuppressLint("NewApi")
    public CCResult afterResult(CC cc, CCResult ccResult) {
        JSONObject params = new JSONObject(cc.getParams());
        String url = params.optString(KEY_URL);
        if (ccResult.isSuccess() && TextUtils.equals(KEY_LOGIN_URL, url)) {
            Log.e(TAG, "-----afterResult---url---:" + url);
            Map<String, Object> data = ccResult.getDataMap();
            mHeaders = (List<String>) data.get(KEY_RESPONSE_HEADERS);
            data.remove(KEY_RESPONSE_HEADERS, mHeaders);
            if (null != mHeaders && mHeaders.size() != 0) {
                for (String header : mHeaders) {
                    Log.e(TAG, "-----afterResult-------header:" + header);
                }
            }
        }
        return ccResult;
    }
}
