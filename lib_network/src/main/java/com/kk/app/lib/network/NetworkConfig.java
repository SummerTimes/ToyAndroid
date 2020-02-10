package com.kk.app.lib.network;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;
import com.kk.app.lib.network.util.DeviceUtil;
import com.kk.app.lib.network.util.SharedPreferenceUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;


/**
 * description:网络请求配置
 * date: 2017/8/23 13:49
 *
 * @author mlp00
 */
public class NetworkConfig {

    private static final String TAG = "NetworkConfig";

    private static NetworkConfig sInstance;

    private static Context mContext;
    private static ArrayMap<String, String> openApiConfig;
    private static ArrayMap<String, String> urlConfig;
    private static Token token;
    public static String deviceNum;
    public static String memberToken = "";
    public static String phone = "";

    private static String networkEnvType = "";

    private NetworkConfig() {
        openApiConfig = new ArrayMap<>();
        urlConfig = new ArrayMap<>();
    }

    public static NetworkConfig getInstance() {
        if (sInstance == null) {
            Log.e(TAG, "NetworkConfig should setup before first call");
        }
        return sInstance;
    }

    /**
     * 设置请求环境
     *
     * @param context
     * @param type
     */
    public static synchronized void setup(Context context, String type) {
        mContext = context;
        networkEnvType = type;
        if (sInstance == null) {
            sInstance = new NetworkConfig();
        }
        getDeviceNum(context);
        sInstance.loadCSV(context, type);
        getToken();
    }

    /**
     * 获取Device Num
     *
     * @param context
     */
    private static void getDeviceNum(Context context) {
        deviceNum = SharedPreferenceUtil.getStringValueFromSP(context, NetworkSpKeys.NETWORK_SP_NAME, NetworkSpKeys.NETWORK_DEVICE_NUM);
        if (TextUtils.isEmpty(deviceNum)) {
            deviceNum = DeviceUtil.getDeviceId(context);
            SharedPreferenceUtil.setStringDataIntoSP(context, NetworkSpKeys.NETWORK_SP_NAME, NetworkSpKeys.NETWORK_DEVICE_NUM, deviceNum);
        }
    }

    /**
     * 加载域名/地址
     *
     * @param context
     * @param type
     */
    private void loadCSV(Context context, String type) {
        try {
            InputStreamReader isr = new InputStreamReader(context.getAssets().open("network_config.csv"));
            BufferedReader reader = new BufferedReader(isr);
            String line;
            while ((line = reader.readLine()) != null) {
                String tokens[] = line.split(";", -1);
                if ("configVal".compareToIgnoreCase(tokens[0]) == 0) {
                    if (type.compareToIgnoreCase(tokens[2]) == 0 && tokens.length > 3) {
                        urlConfig.put(tokens[1], tokens[3]);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    public static Map<String, String> getOpenApiConfig() {
        return openApiConfig;
    }

    /**
     * 设置Token/并保存
     *
     * @param token
     */
    public static void setToken(Token token) {
        NetworkConfig.token = token;
        if (token != null) {
            String strToken = new Gson().toJson(token);
            SharedPreferenceUtil.setStringDataIntoSP(mContext, NetworkSpKeys.NETWORK_SP_NAME, NetworkSpKeys.NETWORK_TOKEN_INFO, strToken);
        }
    }

    /**
     * 获取Token
     *
     * @return
     */
    public static Token getToken() {
        if (token == null) {
            String strToken = SharedPreferenceUtil.getStringValueFromSP(mContext, NetworkSpKeys.NETWORK_SP_NAME, NetworkSpKeys.NETWORK_TOKEN_INFO, null);
            if (!TextUtils.isEmpty(strToken)) {
                token = new Gson().fromJson(strToken, Token.class);
            }
        }
        return token;
    }

    /**
     * 获取网络环境
     *
     * @return
     */
    public static String getNetworkEnvType() {
        return networkEnvType;
    }

    /**
     * 额外设置域名
     *
     * @param key
     * @param value
     */
    public static void setUrl(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            urlConfig.put(key, value);
        }
    }

    /**
     * 根据key获取url
     *
     * @param key
     * @return
     */
    public static String getUrl(String key) {
        String result = "";
        if (urlConfig != null && urlConfig.containsKey(key)) {
            result = urlConfig.get(key);
        }
        return result;
    }

    /**
     * 获取H5/域名
     *
     * @return
     */
    public static String getH5Url() {
        return getUrl(UrlType.H5);
    }

    /**
     * 获取App/域名
     *
     * @return
     */
    public static String getAppMwUrl() {
        return getUrl(UrlType.APP_MW);
    }

}
