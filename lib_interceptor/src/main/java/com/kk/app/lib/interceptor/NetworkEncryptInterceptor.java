package com.kk.app.lib.interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.Chain;
import com.billy.cc.core.component.ICCInterceptor;
import com.google.gson.Gson;
import com.kk.app.lib.interceptor.cache.CacheBlock;
import com.kk.app.lib.interceptor.cache.CacheManager;
import com.kk.app.lib.interceptor.cache.CacheOption;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Iterator;

/**
 * @author billy.qi
 * @datetime: 2017/08/04
 * @desc:网络请求组件的加解密拦截器/默认使用form表单方式发送请求
 */
public class NetworkEncryptInterceptor implements ICCInterceptor {
    /**
     * 对应network组件中保存请求结果的key
     */
    private static final String KEY_RESULT = "result";
    /**
     * 对应network组件中获取参数列表的key
     */
    private static final String KEY_DATA = "data";
    /**
     * 对应network组件中获取url的key
     */
    private static final String KEY_URL = "url";
    private static final String KEY_CONTENT_TYPE = "Content-type";
    /**
     * 对应network组件中获取头信息的key
     */
    private static final String KEY_HEADER = "headers";
    /**
     * 报文加解密密钥
     */
    private static final String KEY_CRYPTO_KEY = "cryptoKey";
    private static final String KEY_CACHE_OPTION = "cacheOption";
    private static final String RES_CODE_SUCCESS = "00100000";

    private static final NetworkEncryptInterceptor INSTANCE = new NetworkEncryptInterceptor();
    private static final String TAG = "xp";

    /**
     * 请求URL
     */
    private String url;
    /**
     * 请求数据
     */
    private String requestData;
    /**
     * 缓存选项
     */
    private CacheOption cacheOption;

    private NetworkEncryptInterceptor() {
    }

    public static NetworkEncryptInterceptor get() {
        return INSTANCE;
    }

    @Override
    public CCResult intercept(Chain chain) {
        CC cc = chain.getCC();
        JSONObject params = new JSONObject(cc.getParams());
        url = getUrl(cc);
        requestData = "";
        if (params.opt(KEY_DATA) != null) {
            requestData = params.opt(KEY_DATA).toString();
        }
        cacheOption = null;

        String cacheStr = params.optString(KEY_CACHE_OPTION);
        if (!TextUtils.isEmpty(cacheStr)) {
            cacheOption = new Gson().fromJson(cacheStr, CacheOption.class);
            if (cacheOption != null) {
                CacheBlock cacheBlock = CacheManager.getInstance().retrieveData(url, requestData, cacheOption.cacheType);
                if (cacheBlock != null) {
                    CCResult ccResult = new CCResult();
                    ccResult.setSuccess(true);
                    ccResult.setCode(CCResult.CODE_SUCCESS);
                    ccResult.addData("resCode", RES_CODE_SUCCESS);
                    ccResult.addData(KEY_RESULT, cacheBlock.getValue());
                    Log.e(TAG, "cache response, cacheType is " + cacheOption.cacheType + ", expire is " + cacheOption.expire
                            + "\nresponse data str=" + cacheBlock.getValue());
                    return ccResult;
                }
            }
        }

        beforeCall(cc);
        CCResult ccResult = chain.proceed();
        return afterResult(cc, ccResult);
    }

    private void beforeCall(CC cc) {
        JSONObject params = new JSONObject(cc.getParams());
        Log.e(TAG, "request: url=" + getUrl(cc) + "\nparams=" + params.toString());
        JSONObject headers = params.optJSONObject(KEY_HEADER);
        boolean hasContentType = hasContentType(headers);
        Object data = params.opt(KEY_DATA);
        if (data != null) {
            try {
                if (headers == null) {
                    headers = new JSONObject();
                    params.put(KEY_HEADER, headers);
                }
                if (!hasContentType) {
                    //默认用form的形式提交
                    headers.put(KEY_CONTENT_TYPE, "application/x-www-form-urlencoded");
                }

                JSONObject dataObj = new JSONObject(data.toString());
                Object otherObj = dataObj.opt("otherresource");
                if (getUrl(cc) != null && getUrl(cc).endsWith("/app/site/queryAdDeploy.htm") && otherObj != null) {
                    JSONObject otherJson = new JSONObject(otherObj.toString());
                    if (!otherJson.has("cookieId")) {
                        CCResult result = CC.obtainBuilder("SensorsComponent")
                                .setActionName("getDistinctId")
                                .build()
                                .call();
                        if (result != null && result.isSuccess() && result.getDataItem("distinctId") != null) {
                            String distinctId = result.getDataItem("distinctId");
                            otherJson.put("cookieId", distinctId);
                            dataObj.put("otherresource", otherJson);
                        }
                    }
                }

                String desStr = DES.encryptDES(dataObj.toString(), params.optString(KEY_CRYPTO_KEY));
                String value = URLEncoder.encode(desStr, "UTF-8");
                params.put(KEY_DATA, "data=" + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getUrl(CC cc) {
        JSONObject params = new JSONObject(cc.getParams());
        return params.optString(KEY_URL);
    }

    private boolean hasContentType(JSONObject headers) {
        boolean findContentType = false;
        if (headers != null) {
            Iterator<String> keys = headers.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (KEY_CONTENT_TYPE.equalsIgnoreCase(key)) {
                    findContentType = true;
                    break;
                }
            }
        }
        return findContentType;
    }

    private CCResult afterResult(CC cc, CCResult ccResult) {
        //params不会为null
        JSONObject params = new JSONObject(cc.getParams());
        JSONObject data = ccResult.getData();
        String url = getUrl(cc);
        Log.e(TAG, "response: url=" + url + "\ndata=" + data);
        if (ccResult.isSuccess()) {
            if (data != null) {
                String content = data.optString(KEY_RESULT);
                if (content != null) {
                    processResponseContent(ccResult, content, params.optString(KEY_CRYPTO_KEY));
                }
            }
        }
        return ccResult;
    }

    private void processResponseContent(CCResult ccResult, String content, String key) {
        JSONObject result;
        try {
            result = new JSONObject(content);
            ccResult.setData(result);
            String resCode = result.optString("resCode");
            String obj = result.optString("obj");
            try {
                //obj里的内容有可能是jsonObject也可能是jsonArray或其它
                if (!TextUtils.isEmpty(obj)) {
                    String jsonStr = DES.decryptDES(obj, key);
                    Log.e(TAG, "response data str=" + jsonStr);
                    ccResult.addData(KEY_RESULT, jsonStr);

                    if (cacheOption != null) {
                        CacheManager.getInstance().cacheData(url, requestData, jsonStr, cacheOption.cacheType, cacheOption.expire);
                    }
                } else {
                    ccResult.setCode(CCResult.CODE_ERROR_BUSINESS);
                    ccResult.setErrorMessage(result.optString("msg"));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!RES_CODE_SUCCESS.equalsIgnoreCase(resCode)) {
                ccResult.setCode(CCResult.CODE_ERROR_BUSINESS);
                ccResult.setErrorMessage(result.optString("msg"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
