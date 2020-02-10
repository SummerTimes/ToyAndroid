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
import com.kk.app.lib.interceptor.NetworkEncryptInterceptor;
import com.kk.app.lib.interceptor.NetworkGsonInterceptor;
import com.kk.app.lib.interceptor.cache.CacheOption;
import com.kk.app.lib.network.constant.NetworkConstant;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 对网络请求组件的封装，用于在项目中对通用的参数进行统一设置
 * 调用者无需知晓网络请求组件的调用方式及拦截器的使用方式
 *
 * @author billy.qi
 * @since 17/8/16 18:14
 */
public class NetworkHelper {

    public static final String HTTP_POST = "post";
    public static final String HTTP_GET = "get";

    private static final String RESULT_KEY = "result";
    /**
     * 默认重试次数
     */
    private static int RETRY_NUM = 1;

    /**
     * 请求openApi接口
     *
     * @param serviceName 服务名
     * @param data        请求数据
     * @param callback    返回
     * @param <T>         数据类型
     */
//    public static <T> String queryOpenApi(String serviceName, JSONObject data, NetworkCallback<T> callback) {
//        return queryOpenApi(null, serviceName, data, callback, true);
//    }

    /**
     * 关联生命周期请求openApi接口
     *
     * @param activity    Activity
     * @param serviceName 服务名
     * @param data        请求数据
     * @param callback    返回
     * @param <T>         数据类型
     */
//    public static <T> String queryOpenApi(Activity activity, String serviceName, JSONObject data, NetworkCallback<T> callback) {
//        return queryOpenApi(activity, serviceName, data, callback, true);
//    }

    /**
     * 关联生命周期请求openApi接口
     *
     * @param fragment    Fragment
     * @param serviceName 服务名
     * @param data        请求数据
     * @param callback    返回
     * @param <T>         数据类型
     */
//    public static <T> String queryOpenApi(Fragment fragment, String serviceName, JSONObject data, NetworkCallback<T> callback) {
//        return queryOpenApi(fragment, serviceName, data, callback, true);
//    }

    /*private static <T> String queryOpenApi(Object activityOrFragment, String serviceName, JSONObject data, NetworkCallback<T> callback, boolean retry) {
        JSONObject config = getOpenApiConfig();
        try {
            if (!TextUtils.isEmpty(serviceName)) {
                config.put("service_name", serviceName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        NetworkComponentCallback<T> componentCallback;
        if (retry) {
            componentCallback = new OpenApiGetTokenAndRetryCallback<>(serviceName, data, callback);
        } else {
            componentCallback = new NetworkComponentCallback<>(callback);
        }

        CC.Builder builder = CC.obtainBuilder("network")
                .setActionName(HTTP_POST)
                .addParam("retry", RETRY_NUM)
                .addParam("headers", getPhoneIdHeader(null))
                .addParam("data", data)
                .addParam("config", config)
                .addInterceptor(new NetworkGsonInterceptor(callback))
                .addInterceptor(NetworkOpenApiInterceptor.get());

        setBuilderRelateLifecycle(builder, activityOrFragment);

        return builder.build().callAsyncCallbackOnMainThread(componentCallback);
    }*/

    /**
     * 同步请求openApi接口
     *
     * @param serviceName 服务名
     * @param data        请求数据
     * @param <T>         数据类型
     */
    /*public static <T> String queryOpenApi(String serviceName, String data) {
        JSONObject config = getOpenApiConfig();
        try {
            if (!TextUtils.isEmpty(serviceName)) {
                config.put("service_name", serviceName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CCResult result = CC.obtainBuilder("network")
                .setActionName(HTTP_POST)
                .setTimeout(0)
                .addParam("retry", RETRY_NUM)
                .addParam("headers", getPhoneIdHeader(null))
                .addParam("data", data)
                .addParam("config", config)
                .addInterceptor(NetworkOpenApiInterceptor.get())
                .build()
                .call();

        if (NetworkConstant.SERVICE_GET_TOKEN.equals(serviceName)) {
            return result.getData().toString();
        } else {
            return handleOpenApiResult(serviceName, data, result);
        }
    }*/

    /*private static String handleOpenApiResult(String serviceName, String originData, CCResult result) {
        if (result.isSuccess() && result.getCode() == CCResult.CODE_SUCCESS) {
            if (result.getData() != null) {
                if (result.getData().has(NetworkConstant.KEY_DATA_RESPONSE)) {
                    result.getData().remove(NetworkConstant.KEY_DATA_RESPONSE);
                }
                try {
                    result.getData().put("resCode", "00100000");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return result.getData().toString();
            }
        } else {
            JSONObject data = result.getData();
            if (data != null && data.has(RESULT_KEY)) {
                JSONObject resultObj = null;
                try {
                    resultObj = new JSONObject(data.optString(RESULT_KEY));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (resultObj != null) {
                    String errorCode = resultObj.optString("errorCode");
                    //没有access_token或access_token失效
                    if (NetworkConstant.EMPTY_TOKEN.equalsIgnoreCase(errorCode) || NetworkConstant.INVALID_TOKEN.equalsIgnoreCase(errorCode)) {
                        NetworkConfig.setToken(null);
                        String tokenResult = queryOpenApi("token", null);
                        try {
                            JSONObject tokenData = new JSONObject(tokenResult);
                            if (!TextUtils.isEmpty(tokenData.optString(RESULT_KEY))) {
                                Token token = new Gson().fromJson(tokenData.optString(RESULT_KEY), Token.class);
                                NetworkConfig.setToken(token);

                                return queryOpenApi(serviceName, originData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        return resultObj.toString();
                    }
                }
            }
        }

        return null;
    }*/

    /**
     * 获取access_token
     */
//    private static void queryOpenApiToken(NetworkCallback<Token> callback) {
//        queryOpenApi(null, "token", null, callback, false);
//    }

    /**
     * 请求中间件接口(POST)
     *
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(String url, JSONObject data, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, callback);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param activity Activity
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(Activity activity, String url, JSONObject data, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, callback);
//
//        setBuilderRelateLifecycle(builder, activity);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(Fragment fragment, String url, JSONObject data, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, callback);
//
//        setBuilderRelateLifecycle(builder, fragment);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }


    /**
     * 请求中间件接口
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(String url, JSONObject data, String method, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, method, callback);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

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
//    public static <T> String query(Activity activity, String url, JSONObject data, String method, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, method, callback);
//
//        setBuilderRelateLifecycle(builder, activity);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param fragment Fragment
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(Fragment fragment, String url, JSONObject data, String method, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, method, callback);
//
//        setBuilderRelateLifecycle(builder, fragment);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

    /*private static <T> CC.Builder genAppMwBuilder(String url, JSONObject data, String method, NetworkCallback<T> callback) {
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

    private static <T> CC.Builder genAppMwBuilder(String url, JSONObject data, NetworkCallback<T> callback) {
        return genAppMwBuilder(url, data, HTTP_POST, callback);
    }*/

    /**
     * 请求中间件接口
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(String url, String data, String method, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, method, callback);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

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
//    public static <T> String query(Activity activity, String url, String data, String method, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, method, callback);
//
//        setBuilderRelateLifecycle(builder, activity);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

    /**
     * 关联生命周期请求中间件接口(POST)
     *
     * @param url      url
     * @param data     请求数据
     * @param method   请求方法（POST/GET）
     * @param callback 返回
     * @param <T>      数据类型
     */
//    public static <T> String query(Fragment fragment, String url, String data, String method, NetworkCallback<T> callback) {
//        CC.Builder builder = genAppMwBuilder(url, data, method, callback);
//
//        setBuilderRelateLifecycle(builder, fragment);
//
//        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
//    }

//    private static <T> CC.Builder genAppMwBuilder(String url, String data, String method, NetworkCallback<T> callback) {
//        String currentTime = String.valueOf(System.currentTimeMillis());
//        return CC.obtainBuilder("network")
//                .setActionName(method)
//                .addParam("retry", RETRY_NUM)
//                .addParam("url", genAppMwUrl(url))
//                .addParam("headers", getHeaderForMiddle(currentTime))
//                .addParam("data", data)
//                .addParam("cryptoKey", DynamicKeyManager.getEncryptKey(currentTime))
//                .addInterceptor(new NetworkGsonInterceptor(callback))
//                .addInterceptor(NetworkEncryptInterceptor.get());
//    }
    public static <T> String query(String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, cacheOption, callback);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    public static <T> String query(Activity activity, String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, cacheOption, callback);

        setBuilderRelateLifecycle(builder, activity);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    public static <T> String query(Fragment fragment, String url, String data, String method, CacheOption cacheOption, NetworkCallback<T> callback) {
        CC.Builder builder = genAppMwBuilder(url, data, method, cacheOption, callback);

        setBuilderRelateLifecycle(builder, fragment);

        return builder.build().callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

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
    public static <T> String queryApi(String url, JSONObject data, NetworkCallback<T> callback) {
        CC cc = CC.obtainBuilder("network")
                .setActionName(HTTP_POST)
                .addParam("retry", RETRY_NUM)
                .addParam("url", url)
                .addParam("headers", getPhoneIdHeader(null))
                .addParam("data", data)
                .addInterceptor(new NetworkGsonInterceptor(callback))
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
    public static <T> String queryApi(String url, String data, String method, NetworkCallback<T> callback) {
        CC cc = CC.obtainBuilder("network")
                .setActionName(method)
                .addParam("retry", RETRY_NUM)
                .addParam("url", url)
                .addParam("headers", getHeader(null))
                .addParam("data", data)
                .addInterceptor(new NetworkGsonInterceptor(callback))
                .build();

        return cc.callAsyncCallbackOnMainThread(new NetworkComponentCallback<>(callback));
    }

    private static JSONObject getHeader(JSONObject header) {
        if (header == null) {
            header = new JSONObject();
        }
        try {
            header.put("Connection", "Keep-Alive");
            header.put("Host", "www.wanandroid.com");
            header.put("Accept-Encoding", "gzip, deflate");
            header.put("Cookie", "gzip, deflate");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return header;
    }

    private static String getServiceResponseData(JSONObject responseObj) {
        String rtn = null;
        if (responseObj != null) {
            rtn = responseObj.optString("result");
        }

        return rtn;
    }

    /*private static JSONObject getOpenApiConfig() {
        Map<String, String> openApiConfig = NetworkConfig.getOpenApiConfig();
        JSONObject config = new JSONObject();
        try {
            Set<String> keys = openApiConfig.keySet();
            for (String key : keys) {
                config.put(key, openApiConfig.get(key));
            }
            if (NetworkConfig.getToken() != null) {
                config.put("access_token", NetworkConfig.getToken().getAccessToken());
                config.put("tokenKey", NetworkConfig.getToken().getTokenKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return config;
    }*/

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
            header.put("deviceKey", DES.encryptDES(NetworkConfig.deviceNum, DynamicKeyManager.getEncryptKey(currentTime)));
            header.put("memberToken", DES.encryptDES(NetworkConfig.memberToken, DynamicKeyManager.getEncryptKey(currentTime)));
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
     * 获取access_token后重新发送openApi请求
     *
     * @param <T>
     */
    /*private static class OpenApiGetTokenAndRetryCallback<T> extends NetworkComponentCallback<T> {

        String serviceName;
        protected JSONObject data;

        OpenApiGetTokenAndRetryCallback(String serviceName, JSONObject data, NetworkCallback<T> callback) {
            super(callback);
            this.serviceName = serviceName;
            this.data = data;
        }

        @Override
        public void onResult(final CC cc, final CCResult rawResult) {
            if (!(rawResult.isSuccess() && rawResult.getCode() == CCResult.CODE_SUCCESS)) {
                JSONObject data = rawResult.getData();
                if (data != null) {
                    if (data.has(RESULT_KEY)) {
                        JSONObject resultObj = null;
                        try {
                            resultObj = new JSONObject(data.optString(RESULT_KEY));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (resultObj != null) {
                            String errorCode = resultObj.optString("errorCode");
                            //没有access_token或access_token失效
                            if (NetworkConstant.EMPTY_TOKEN.equalsIgnoreCase(errorCode) || NetworkConstant.INVALID_TOKEN.equalsIgnoreCase(errorCode)) {
                                NetworkConfig.setToken(null);
                                //获取token后重试
                                queryOpenApiToken(new NetworkCallback<Token>() {
                                    @Override
                                    public void onSuccess(@NonNull CCResult rawResult, Token token) {
                                        if (token != null) {
                                            NetworkConfig.setToken(token);
                                        }
                                    }

                                    @Override
                                    public void onFailed(@NonNull CCResult result) {
                                    }

                                    @Override
                                    public void onFinally(@NonNull CCResult result) {
                                        if (NetworkConfig.getToken() != null) {//获取token成功
                                            queryOpenApi(null, serviceName
                                                    , OpenApiGetTokenAndRetryCallback.this.data
                                                    , callback
                                                    , false);//重试过一次后就不再重试了
                                        } else {//获取token失败
                                            OpenApiGetTokenAndRetryCallback.super.onResult(cc, rawResult);
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }

                }
            }
            super.onResult(cc, rawResult);
        }
    }*/

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
