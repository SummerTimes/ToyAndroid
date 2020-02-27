package com.kk.app.lib.interceptor;


import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.Chain;
import com.billy.cc.core.component.ICCInterceptor;

import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author billy.qi
 * @datetime: 2017/08/04
 * @desc:网络请求组件的openApi拦截器
 */
public class NetworkOpenApiInterceptor implements ICCInterceptor {

    private static final NetworkOpenApiInterceptor INSTANCE = new NetworkOpenApiInterceptor();
    private static final String TAG = "openApi";
    private static final String KEY_RESULT = "result";//对应network组件中保存请求结果的key
    private static final String KEY_URL = "url";//对应network组件中获取url的key

    private NetworkOpenApiInterceptor() {
    }

    public static NetworkOpenApiInterceptor get() {
        return INSTANCE;
    }

    @Override
    public CCResult intercept(Chain chain) {
        CC cc = chain.getCC();
        beforeCall(cc);
        CCResult ccResult = chain.proceed();
        return afterResult(cc, ccResult);
    }

    private void beforeCall(CC cc) {
        JSONObject params = new JSONObject(cc.getParams());
        try {
            JSONObject config = params.optJSONObject("config");
            if (config != null) {
                Log.i(TAG, "request: service_name=" + config.optString("service_name")
                        + "\nparams=" + params.toString());
                String url = makeOpenApiRequest(config);
                params.put(KEY_URL, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CCResult afterResult(CC cc, CCResult ccResult) {
        JSONObject data = ccResult.getData();
        JSONObject params = new JSONObject(cc.getParams());
        Log.i(TAG, "response: url=" + params.optString(KEY_URL) + "\ndata=" + data);
        if (ccResult.isSuccess()) {
            if (data != null) {
                String result = data.optString(KEY_RESULT);
                try {
                    JSONObject obj = new JSONObject(result);
                    processResult(ccResult, obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ccResult;
    }

    private void processResult(CCResult ccResult, JSONObject objMain) {

        if (objMain.has("status_error")) {
            ccResult.setSuccess(false);
            ccResult.setCode(CCResult.CODE_ERROR_BUSINESS);
            ccResult.setErrorMessage("亲，服务器故障，请稍后再试...");
        } else if (objMain.has("errorCode")) {
            ccResult.setSuccess(false);
            ccResult.setCode(CCResult.CODE_ERROR_BUSINESS);
        } else if (objMain.has("accessToken")) {
        } else {
            String rescode = null;
            if (objMain.has("resCode")) {
                rescode = objMain.optString("resCode");
            }
            if ("00100000".equals(rescode)) {
                if (objMain.has("obj")) {
                    try {
                        ccResult.addData(KEY_RESULT, objMain.optString("obj"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //memberToken失效
                if ("00100009".equals(rescode) || "05111001".equals(rescode) || "05111002".equals(rescode) || "1080002".equals(rescode)) {
//                    BLAppContext.getInstance().cleanUserInfo();
                }
                ccResult.setSuccess(false);
                ccResult.setCode(CCResult.CODE_ERROR_BUSINESS);
                ccResult.setErrorMessage(objMain.optString("msg"));
            }
        }
    }

    private String makeOpenApiRequest(JSONObject config) {
        try {
            config.put("timestamp", String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = null;
        String sign;
        try {
            if ("token".equals(config.optString("service_name"))) {
                url = config.optString("url") + "/getToken.htm" + "?";
                sign = MD5Utils.string2SHA1(
                        config.opt("grant_type") + config.optString("appid")
                                + config.optString("secret")
                                + config.optString("timestamp") + config.optString("salt")
                                + config.optString("sn") + config.optString("channelId"),
                        32);
                config.put("sign", sign);
                StringBuilder sb = new StringBuilder();
                Iterator<String> iterator = config.keys();
                while (iterator.hasNext()) {
                    String s = iterator.next();
                    if (!"url".equals(s) && !"tokenKey".equals(s)) {
                        if (sb.length() > 0) {
                            sb.append("&");
                        }
                        sb.append(s).append("=").append(config.optString(s));
                    }
                }
                url += sb.toString();
            } else {
                url = config.optString("url") + "/service.htm?";
                sign = MD5Utils.string2SHA1(
                        config.optString("tokenKey", "0") + config.optString("service_name")
                                + config.optString("timestamp") + config.optString("salt")
                                + config.optString("sn") + config.optString("channelId"),
                        32);
                config.put("sign", sign);
                StringBuilder sb = new StringBuilder();
                Iterator<String> iterator = config.keys();
                while (iterator.hasNext()) {
                    String s = iterator.next();
                    if (!"url".equals(s) && !"tokenKey".equals(s)) {
                        if (sb.length() > 0) {
                            sb.append("&");
                        }
                        sb.append(s).append("=").append(config.optString(s));
                    }
                }
                url += "openapi_params=" + Base64.encode(sb.toString().getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
