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
 * @author kk
 * @datetime: 2020/4/24
 * @desc: 网络请求配置
 */
public class NetworkConfig {

    public static final String TAG = "NetworkConfig";

    public static Context mContext;
    public static NetworkConfig sInstance;
    public static String memberToken = "";
    public static String networkEnvType = "";
    public static ArrayMap<String, String> urlConfig;

    private NetworkConfig() {
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
        sInstance.loadCSV(context, type);
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
                String[] configs = line.split(";", -1);
                if ("configVal".compareToIgnoreCase(configs[0]) == 0) {
                    if (type.compareToIgnoreCase(configs[2]) == 0 && configs.length > 3) {
                        urlConfig.put(configs[1], configs[3]);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
