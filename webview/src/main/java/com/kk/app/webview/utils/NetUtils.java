package com.kk.app.webview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc网络工具类
 */
public class NetUtils {

    /**
     * 检查当前的网络（wifi及移动网络）
     *
     * @param context
     * @return
     */
    public static boolean checkNet(Context context) {
        Context applicationContext = context.getApplicationContext();
        boolean wifiConnection = isWIFIConnection(applicationContext);
        boolean mobileConnection = isMobileConnection(applicationContext);
        return wifiConnection || mobileConnection;
    }

    /**
     * 判断wifi是否连接
     *
     * @param context
     * @return
     */
    public static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    /**
     * 判断是否是手机移动网络
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (networkInfo != null) {
            return networkInfo.isConnected();
        }
        return false;
    }


}
