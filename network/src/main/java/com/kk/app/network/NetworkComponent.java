package com.kk.app.network;

import android.text.TextUtils;
import android.util.Log;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author kk
 * @datetime 2019-08-08
 * @desc 使用okHttp实现的网络请求组件:https://github.com/square/okhttp/wiki/Recipes
 */
public class NetworkComponent implements IComponent {

    private static final String TAG = "xp";

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private OkHttpClient client;


    public NetworkComponent() {
        createOkHttpClient(10, 10, 30);
    }

    @Override
    public String getName() {
        return NetworkConstant.KRY_MINE_COMPONENT;
    }

    @Override
    public boolean onCall(CC cc) {
        CCResult result;
        try {
            String action = cc.getActionName();
            JSONObject params = new JSONObject(cc.getParams());
            String url = params.optString(NetworkConstant.KEY_URL);
            Log.e(TAG, "-----params-----" + params);
            if (NetworkConstant.KRY_ACTION_GET.equalsIgnoreCase(action)) {
                boolean networkConnected = NetworkUtil.isNetworkConnected(cc.getContext());
                if (networkConnected) {
                    //有网络才发起请求
                    result = get(url, params);
                } else {
                    result = CCResult.error(cc.getContext().getString(R.string.network_unconnected));
                }
                Log.e(TAG, "-----GET---result--" + result);
            } else if (NetworkConstant.KRY_ACTION_POST.equalsIgnoreCase(action)) {
                boolean networkConnected = NetworkUtil.isNetworkConnected(cc.getContext());
                if (networkConnected) {
                    //有网络才发起请求
                    result = post(url, params);
                } else {
                    result = CCResult.error(cc.getContext().getString(R.string.network_unconnected));
                }
                Log.e(TAG, "-----POST---result--" + result);
            } else if (NetworkConstant.KRY_ACTION_SETTING.equalsIgnoreCase(action)) {
                initOkHttpClient(params);
                result = CCResult.success();
            } else {
                result = CCResult.error("action not support for:" + action);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = CCResult.error(cc.getContext().getString(R.string.network_connect_failed));
        }
        CC.sendCCResult(cc.getCallId(), result);
        return false;
    }

    /**
     * 初始化OkHttp
     *
     * @param params
     */
    private void initOkHttpClient(JSONObject params) {
        int connectTimeout = params.optInt(NetworkConstant.KEY_CONNECT_TIMEOUT, 10);
        int writeTimeout = params.optInt(NetworkConstant.KEY_WRITE_TIMEOUT, 10);
        int readTimeout = params.optInt(NetworkConstant.KEY_READ_TIMEOUT, 30);
        createOkHttpClient(connectTimeout, writeTimeout, readTimeout);
    }

    /**
     * 创建OkHttpClient
     *
     * @param connectTimeout
     * @param writeTimeout
     * @param readTimeout
     */
    private void createOkHttpClient(int connectTimeout, int writeTimeout, int readTimeout) {
        client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .hostnameVerifier(NetworkUtil.getHostnameVerifier())
                .sslSocketFactory(NetworkUtil.getSSLSocketFactory(), NetworkUtil.getX509TrustManager())
                .build();
    }

    /**
     * get 请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    private CCResult get(String url, JSONObject params) throws IOException {
        Object data = params.opt(NetworkConstant.KEY_DATA);
        if (data != null) {
            if (data instanceof JSONObject) {
                //在url后面追加参数列表
                url = buildGetUrl(url, (JSONObject) data);
            } else {
                if (!url.contains("?")) {
                    url += "?";
                }
                url += data;
            }
        }
        Log.e(TAG, "---get------url-----:" + url);
        Request.Builder builder = new Request.Builder().url(url).get();
        addHeaders(builder, params);
        Request request = builder.build();
        Response response = client.newCall(request).execute();
        return processResponse(url, params, response);
    }

    /**
     * 拼接参数：url后面追加参数列表
     *
     * @param url
     * @param data
     * @return
     */
    private String buildGetUrl(String url, JSONObject data) {
        StringBuilder sb = new StringBuilder(url);
        boolean first = !url.contains("?");
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            if (first) {
                first = false;
                sb.append("?");
            } else {
                sb.append("&");
            }
            String key = keys.next();
            sb.append(key).append('=').append(data.optString(key));
        }
        return sb.toString();
    }

    /**
     * 过程响应
     *
     * @param url
     * @param params
     * @param response
     * @return
     * @throws IOException
     */
    private CCResult processResponse(String url, JSONObject params, Response response) throws IOException {
        CCResult result = getResult(response);
        //请求失败，处理重试逻辑
        if (!result.isSuccess()) {
            int retry = params.optInt(NetworkConstant.KEY_RETRY, 0);
            retry = Math.min(Math.max(retry, 0), NetworkConstant.MAX_RETRY_COUNT);
            int retryCount = params.optInt(NetworkConstant.PRIVATE_KEY_RETRY_COUNT, 0);
            retryCount = Math.max(retryCount, 0);
            if (retry > retryCount) {
                try {
                    retryCount++;
                    params.put(NetworkConstant.PRIVATE_KEY_RETRY_COUNT, retryCount);
                } catch (Exception e) {
                    e.printStackTrace();
                    //设置重试次数失败，为避免死循环，不进行重试
                    return result;
                }
                //开始重试
                return post(url, params);
            }
        }
        return result;
    }

    /**
     * post 请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    private CCResult post(String url, JSONObject params) throws IOException {
        Request.Builder builder = new Request.Builder();
        addHeaders(builder, params);
        RequestBody body = buildRequestBody(params);
        Request request = builder
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return processResponse(url, params, response);
    }


    /**
     * 构建Body
     *
     * @param params
     * @return
     */
    private RequestBody buildRequestBody(JSONObject params) {
        MediaType mediaType;
        String contentType = params.optString(NetworkConstant.PRIVATE_KEY_CONTENT_TYPE);
        if (!TextUtils.isEmpty(contentType)) {
            mediaType = MediaType.parse(contentType);
        } else {
            mediaType = JSON;
        }
        Object data = params.opt(NetworkConstant.KEY_DATA);
        String content;
        if (data == null) {
            if (JSON.equals(mediaType)) {
                content = "{}";
            } else {
                content = "";
            }
        } else {
            content = data.toString();
        }
        FormBody.Builder body = new FormBody.Builder();
        Map<String, String> retMap = new Gson().fromJson(content, new TypeToken<Map<String, String>>() {
        }.getType());
        for (String key : retMap.keySet()) {
            body.add(key, retMap.get(key));
        }
        return body.build();
//        return RequestBody.create(mediaType, content);
    }


    /**
     * 给网络请求添加head
     */
    private void addHeaders(Request.Builder builder, JSONObject params) {
        try {
            String headers = params.optString(NetworkConstant.KEY_HEADER);
            if (!TextUtils.isEmpty(headers)) {
                JSONObject heads = new JSONObject(headers);
                Iterator<String> keys = heads.keys();
                String key, value;
                while (keys.hasNext()) {
                    key = keys.next();
                    value = heads.optString(key);
                    Log.e("xp", "------key-----–" + key);
                    Log.e("xp", "------value-----–" + value);
                    if (key.contains("Cookie")) {
                        builder.addHeader("Cookie", value);
                    } else {
                        builder.addHeader(key, value);
                    }
                    if (NetworkConstant.PRIVATE_KEY_CONTENT_TYPE.equalsIgnoreCase(key)) {
                        try {
                            params.put(NetworkConstant.PRIVATE_KEY_CONTENT_TYPE, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建CCResult对象
     *
     * @param response 网络请求的response
     * @return CCResult对象
     */
    private CCResult getResult(Response response) {
        int code = response.code();
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                try {
                    String content = responseBody.string();
                    Map<String, Object> result = new HashMap<>();
                    result.put(NetworkConstant.KEY_RESULT, content);
                    if (!response.headers("Set-Cookie").isEmpty()) {
                        List<String> cookies = response.headers("Set-Cookie");
                        result.put(NetworkConstant.KEY_RESPONSE_HEADERS, cookies);
                    }
                    return CCResult.success(result);
                } catch (Exception e) {
                    e.printStackTrace();
                    return CCResult.error(NetworkConstant.KEY_HTTP_CODE, code);
                }
            }
            return CCResult.success();
        }
        CCResult result = CCResult.error(NetworkConstant.KEY_HTTP_CODE, code);
        result.setErrorMessage("http response:" + code);
        return result;
    }
}
