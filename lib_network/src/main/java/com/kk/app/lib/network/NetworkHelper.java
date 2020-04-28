package com.kk.app.lib.network;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.google.gson.Gson;
import com.kk.app.lib.interceptor.DES;
import com.kk.app.lib.interceptor.NetworkCookieInterceptor;
import com.kk.app.lib.interceptor.NetworkEncryptInterceptor;
import com.kk.app.lib.interceptor.NetworkGsonInterceptor;
import com.kk.app.lib.interceptor.NetworkOpenApiInterceptor;
import com.kk.app.lib.interceptor.cache.CacheOption;
import com.kk.app.lib.network.constant.NetworkConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * @author kk
 * @datetime: 2020/4/24
 * @desc: 1.对网络请求组件的封装，用于在项目中对通用的参数进行统一设置
 * 2.调用者无需知晓网络请求组件的调用方式及拦截器的使用方式
 */
public class NetworkHelper {

    /**
     * 默认重试次数
     */
    private static int RETRY_NUM = 1;

    public static final String HTTP_GET = "get";
    public static final String HTTP_POST = "post";
    public static final String RESULT_KEY = "result";


    /**
     * 请求中间件接口(POST)
     *
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(String url, JSONObject data, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, callback);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Activity activity, String url, JSONObject data, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, callback);

        setBuilderRelateLifecycle(builder, activity);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Fragment fragment, String url, JSONObject data, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, callback);

        setBuilderRelateLifecycle(builder, fragment);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 请求中间件接口 (POST)
     *
     * @param url
     * @param data
     * @param callback
     * @param <T>
     * @return
     */
    private static <T> CC.Builder genAppMwBuilder(String url, JSONObject data, NetworkCallback<T> callback) {
        return genAppMwBuilder(url, data, HTTP_POST, callback);
    }


    /**
     * 请求中间件接口
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(String url, JSONObject data, String method, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, callback);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 关联生命周期请求中间件接口
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Activity activity, String url, JSONObject data, String method, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, callback);

        setBuilderRelateLifecycle(builder, activity);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 关联生命周期请求中间件接口
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Fragment fragment, String url, JSONObject data, String method, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, callback);

        setBuilderRelateLifecycle(builder, fragment);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }


    /**
     * 请求中间件接口（POST/GET）
     *
     * @param url
     * @param data
     * @param method
     * @param callback
     * @param <T>
     * @return
     */
    private static <T> CC.Builder genAppMwBuilder(String url, JSONObject data, String method, NetworkCallback<T> callback) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return CC.obtainBuilder("network")
                .setActionName(method)
                .addParam("retry", RETRY_NUM)
                .addParam("url", genAppMwUrl(url))
                .addParam("headers", getHeaderForMiddle(currentTime))
                .addParam("data", data)
                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
                .addInterceptor(new NetworkGsonInterceptor(callback))
                .addInterceptor(NetworkEncryptInterceptor.get());
    }


    /**
     * 请求中间件接口
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(String url, String data, String method, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, callback);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Activity activity, String url, String data, String method, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, callback);

        setBuilderRelateLifecycle(builder, activity);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Fragment fragment, String url, String data, String method, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, callback);

        setBuilderRelateLifecycle(builder, fragment);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 请求中间件接口（POST/GET）
     *
     * @param url
     * @param data
     * @param method
     * @param callback
     * @param <T>
     * @return
     */
    private static <T> CC.Builder genAppMwBuilder(String url, String data, String method, NetworkCallback<T> callback) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return CC.obtainBuilder("network")
                .setActionName(method)
                .addParam("retry", RETRY_NUM)
                .addParam("url", genAppMwUrl(url))
                .addParam("headers", getHeaderForMiddle(currentTime))
                .addParam("data", data)
                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
                .addInterceptor(new NetworkGsonInterceptor(callback))
                .addInterceptor(NetworkEncryptInterceptor.get());
    }

    /**
     *
     * @param url
     * @param data
     * @param method
     * @param cacheOption
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> String query(String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, cacheOption, callback);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     *
     * @param activity
     * @param url
     * @param data
     * @param method
     * @param cacheOption
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> String query(Activity activity, String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, cacheOption, callback);

        setBuilderRelateLifecycle(builder, activity);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     *
     * @param fragment
     * @param url
     * @param data
     * @param method
     * @param cacheOption
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> String query(Fragment fragment, String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, cacheOption, callback);

        setBuilderRelateLifecycle(builder, fragment);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }


    /**
     *
     * @param url
     * @param data
     * @param method
     * @param cacheOption
     * @param callback
     * @param <T>
     * @return
     */
    private static <T> CC.Builder genAppMwBuilder(String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        CC.Builder builder = CC.obtainBuilder("network")
                .setActionName(method)
                .addParam("retry", RETRY_NUM)
                .addParam("url", genAppMwUrl(url))
                .addParam("headers", getHeaderForMiddle(currentTime))
                .addParam("data", data)
                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
                .addInterceptor(new NetworkGsonInterceptor(callback))
                .addInterceptor(NetworkEncryptInterceptor.get());

        if (cacheOption != null && !TextUtils.isEmpty(cacheOption.cacheType)) {
            builder.addParam("cacheOption", new Gson().toJson(cacheOption));
        }

        return builder;
    }

    /**
     * 同步请求中间件接口
     *
     * @param url    url
     * @param data   请求数据
     * @param method 请求方法（POST/GET）
     */
    public static String query(String url, String data, String method) {
        if (TextUtils.isEmpty(method)) {
            method = NetworkHelper.HTTP_POST;
        } else {
            method = NetworkHelper.HTTP_POST.equals(method) ? NetworkHelper.HTTP_POST : NetworkHelper.HTTP_GET;
        }

        CC.Builder builder = getAppMwBuilder(url, data, method);

        CCResult result = builder.build().call();

        if (result != null && result.getData() != null) {
            if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
            }
            return result.getData().toString();
        }

        return null;
    }

    /**
     * 同步请求中间件接口
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     */
    public static String query(Activity activity, String url, String data, String method) {
        if (TextUtils.isEmpty(method)) {
            method = NetworkHelper.HTTP_POST;
        } else {
            method = NetworkHelper.HTTP_POST.equals(method) ? NetworkHelper.HTTP_POST : NetworkHelper.HTTP_GET;
        }

        CC.Builder builder = getAppMwBuilder(url, data, method);

        setBuilderRelateLifecycle(builder, activity);

        CCResult result = builder.build().call();

        if (result != null && result.getData() != null) {
            if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
            }
            return result.getData().toString();
        }

        return null;
    }

    /**
     * 同步请求中间件接口
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     */
    public static String query(Fragment fragment, String url, String data, String method) {
        if (TextUtils.isEmpty(method)) {
            method = NetworkHelper.HTTP_POST;
        } else {
            method = NetworkHelper.HTTP_POST.equals(method) ? NetworkHelper.HTTP_POST : NetworkHelper.HTTP_GET;
        }

        CC.Builder builder = getAppMwBuilder(url, data, method);

        setBuilderRelateLifecycle(builder, fragment);

        CCResult result = builder.build().call();

        if (result != null && result.getData() != null) {
            if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
            }
            return result.getData().toString();
        }

        return null;
    }

    /**
     *
     * @param url
     * @param data
     * @param method
     * @return
     */
    private static CC.Builder getAppMwBuilder(String url, String data, String method) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        return CC.obtainBuilder("network")
                .setActionName(method)
                .setTimeout(0)
                .addParam("retry", RETRY_NUM)
                .addParam("url", genAppMwUrl(url))
                .addParam("headers", getHeaderForMiddle(currentTime))
                .addParam("data", data)
                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
                .addInterceptor(NetworkEncryptInterceptor.get());
    }


    public static String query(String url, String data, String method, CacheOption cacheOption) {
        if (TextUtils.isEmpty(method)) {
            method = NetworkHelper.HTTP_POST;
        } else {
            method = NetworkHelper.HTTP_POST.equals(method) ? NetworkHelper.HTTP_POST : NetworkHelper.HTTP_GET;
        }

        CC.Builder builder = getAppMwBuilder(url, data, method, cacheOption);

        CCResult result = builder.build().call();

        if (result != null && result.getData() != null) {
            if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
            }
            return result.getData().toString();
        }

        return null;
    }

    /**
     * 同步请求中间件接口
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     */
    public static String query(Activity activity, String url, String data, String method, CacheOption cacheOption) {
        if (TextUtils.isEmpty(method)) {
            method = NetworkHelper.HTTP_POST;
        } else {
            method = NetworkHelper.HTTP_POST.equals(method) ? NetworkHelper.HTTP_POST : NetworkHelper.HTTP_GET;
        }

        CC.Builder builder = getAppMwBuilder(url, data, method, cacheOption);

        setBuilderRelateLifecycle(builder, activity);

        CCResult result = builder.build().call();

        if (result != null && result.getData() != null) {
            if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
            }
            return result.getData().toString();
        }

        return null;
    }

    /**
     * 同步请求中间件接口
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     */
    public static String query(Fragment fragment, String url, String data, String method, CacheOption cacheOption) {
        if (TextUtils.isEmpty(method)) {
            method = NetworkHelper.HTTP_POST;
        } else {
            method = NetworkHelper.HTTP_POST.equals(method) ? NetworkHelper.HTTP_POST : NetworkHelper.HTTP_GET;
        }

        CC.Builder builder = getAppMwBuilder(url, data, method, cacheOption);

        setBuilderRelateLifecycle(builder, fragment);

        CCResult result = builder.build().call();

        if (result != null && result.getData() != null) {
            if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
            }
            return result.getData().toString();
        }

        return null;
    }

    private static CC.Builder getAppMwBuilder(String url, String data, String method, CacheOption cacheOption) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        CC.Builder builder = CC.obtainBuilder("network")
                .setActionName(method)
                .setTimeout(0)
                .addParam("retry", RETRY_NUM)
                .addParam("url", genAppMwUrl(url))
                .addParam("headers", getHeaderForMiddle(currentTime))
                .addParam("data", data)
                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
                .addInterceptor(NetworkEncryptInterceptor.get());

        if (cacheOption != null && !TextUtils.isEmpty(cacheOption.cacheType)) {
            builder.addParam("cacheOption", new Gson().toJson(cacheOption));
        }

        return builder;
    }

    /**
     * 请求中间件接口
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(String url, String data, String method, int retryNum, NetworkCallback<T> callback) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        CC.Builder builder = getAppMwBuilder(url, data, method, retryNum, callback, currentTime);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 请求中间件接口
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param retryNum 重试次数
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Activity activity, String url, String data, String method, int retryNum, NetworkCallback<T> callback) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        CC.Builder builder = getAppMwBuilder(url, data, method, retryNum, callback, currentTime);

        setBuilderRelateLifecycle(builder, activity);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 请求中间件接口
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param retryNum 重试次数
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String query(Fragment fragment, String url, String data, String method, int retryNum, NetworkCallback<T> callback) {
        String currentTime = String.valueOf(System.currentTimeMillis());
        CC.Builder builder = getAppMwBuilder(url, data, method, retryNum, callback, currentTime);

        setBuilderRelateLifecycle(builder, fragment);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    private static <T> CC.Builder getAppMwBuilder(String url, String data, String method, int retryNum, NetworkCallback<T> callback, String currentTime) {
        return CC.obtainBuilder("network")
                .setActionName(method)
                .addParam("retry", retryNum)
                .addParam("url", genAppMwUrl(url))
                .addParam("headers", getHeaderForMiddle(currentTime))
                .addParam("data", data)
                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
                .addInterceptor(new NetworkGsonInterceptor(callback))
                .addInterceptor(NetworkEncryptInterceptor.get());
    }

    /**
     * 请求中台接口
     *
     * @param url  url
     * @param data 请求数据
     * @return 返回数据
     */
    public static String queryServiceJson(String url, String data) {
        CCResult result = CC.obtainBuilder("network")
                .setActionName(HTTP_POST)
                .setTimeout(0)
                .addParam("retry", RETRY_NUM)
                .addParam("url", url)
                .addParam("headers", getPhoneIdHeader(null))
                .addParam("data", data)
                .build()
                .call();

        if (result != null) {
            if (result.isSuccess()) {
                return getServiceResponseData(result.getData());
            } else {
                return result.getErrorMessage();
            }
        } else {
            return "网络连接失败";
        }
    }

    /**
     * 请求中台接口
     *
     * @param url  url
     * @param data 请求数据
     * @return 返回数据
     */
    public static String queryServiceForm(String url, String data) {
        JSONObject header = new JSONObject();
        try {
            header.put("Content-type", "application/x-www-form-urlencoded");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CCResult result = CC.obtainBuilder("network")
                .setActionName(HTTP_POST)
                .setTimeout(0)
                .addParam("retry", RETRY_NUM)
                .addParam("url", url)
                .addParam("headers", getPhoneIdHeader(header))
                .addParam("data", data)
                .build()
                .call();

        if (result != null) {
            if (result.isSuccess()) {
                return getServiceResponseData(result.getData());
            } else {
                return result.getErrorMessage();
            }
        } else {
            return "网络连接失败";
        }
    }

    /**
     * 请求Api接口(无加密)
     *
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String queryApi(String url, JSONObject data, String header, NetworkCallback<T> callback) {
        CC cc = CC.obtainBuilder("network")
                .setActionName(HTTP_POST)
                .addParam("retry", RETRY_NUM)
                .addParam("url", url)
                .addParam("headers", header)
                .addParam("data", data)
                .addInterceptor(new NetworkGsonInterceptor(callback))
//                .addInterceptor(NetworkCookieInterceptor.get())
                .build();
        return cc.callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    /**
     * 请求Api接口(无加密)
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
    public static <T> String queryApi(String url, String data, String header, String method, NetworkCallback<T> callback) {
        CC cc = CC.obtainBuilder("network")
                .setActionName(method)
                .addParam("retry", RETRY_NUM)
                .addParam("url", url)
                .addParam("headers", header)
                .addParam("data", data)
                .addInterceptor(new NetworkGsonInterceptor(callback))
//                .addInterceptor(NetworkCookieInterceptor.get())
                .build();
        return cc.callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    private static String getServiceResponseData(JSONObject responseObj) {
        String rtn = null;
        if (responseObj != null) {
            rtn = responseObj.optString("result");
        }
        return rtn;
    }


    /**
     * 中间件请求的header
     *
     * @param currentTime 当前时间
     * @return 报文头
     */
    private static JSONObject getHeaderForMiddle(String currentTime) {
        JSONObject header = new JSONObject();
        try {
            header.put("Connection", "Keep-Alive");
            header.put("chnflg", "Android");
            //2.5.0以下的密钥不一样
            header.put("version", getCurrentVersionName(CC.getApplication()));
            header.put("timeStamp", currentTime);
            header.put("deskeyVersion", DynamicKeyManager.KEY_VERSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getPhoneIdHeader(header);
    }

    private static JSONObject getPhoneIdHeader(JSONObject header) {
        if (header == null) {
            header = new JSONObject();
        }
        try {
            header.put("Content-Type", "application/x-www-form-urlencoded");
            header.put("Cookie", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return header;
    }

    /**
     * 调用Network Component 组件CallBack
     *
     * @param <T>
     */
    private static class NetworkComponentCallback<T> implements IComponentCallback {

        NetworkCallback<T> callback;

        NetworkComponentCallback(NetworkCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onResult(CC cc, CCResult result) {
            if (callback == null) {
                return;
            }
            try {
                if (result.isSuccess() && result.getCode() == CCResult.CODE_SUCCESS) {
                    JSONObject data = result.getData();
                    T t = null;
                    if (data.has(RESULT_KEY)) {
                        try {
                            t = (T) data.opt(RESULT_KEY);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    callback.onSuccess(result, t);
                } else {
                    callback.onFailed(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                callback.onFinally(result);
            }
        }
    }

    /**
     * 获取Url 是否添加前缀
     * 1：包含前缀不拼接AppMwUrl
     * 2：不包含添加AppMwUrl 前缀
     *
     * @param url
     * @return
     */
    @NonNull
    private static String genAppMwUrl(String url) {
        if (!url.startsWith(NetworkConstant.HTTP) && !url.startsWith(NetworkConstant.HTTPS)) {
            url = NetworkConfig.getAppMwUrl() + url;
        }
        return url;
    }

    /***
     * 获取当前App的VersionName
     * @param context 当前上下文
     * @return
     */
    private static String getCurrentVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置生成关联生命周期
     *
     * @param builder
     * @param activityOrFragment
     */
    private static void setBuilderRelateLifecycle(CC.Builder builder, Object activityOrFragment) {
        if (activityOrFragment instanceof Activity) {
            builder.cancelOnDestroyWith((Activity) activityOrFragment);
        } else if (activityOrFragment instanceof Fragment) {
            builder.cancelOnDestroyWith((Fragment) activityOrFragment);
        }
    }
}
