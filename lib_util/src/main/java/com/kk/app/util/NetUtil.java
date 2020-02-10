package com.kk.app.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 *
 */
public class NetUtil {

    private static final String TAG = "NetUtil";
    private static final int CHINA_MOBILE = 1;// 中国移动
    private static final int CHINA_TELECOM = 2;// 中国电信
    private static final int CHINA_UNICOM = 3;// 中国联通

    private NetUtil() {

    }

    /**
     * 检查当前的网络（wifi及移动网络）
     *
     * @param context application
     * @return 是否有网络
     */
    public static boolean checkNet(Context context) {
        // 获取到wifi和mobile连接方式
        Context applicationContext = context.getApplicationContext();
        boolean wifiConnection = isWIFIConnection(applicationContext);
        boolean mobileConnection = isMobileConnection(applicationContext);

        return !(!wifiConnection && !mobileConnection);
    }

    /**
     * 判断wifi是否连接
     *
     * @param context application
     * @return 是否有WI-FI
     */
    public static boolean isWIFIConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (manager != null) {
            networkInfo = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        }

        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 判断是否是手机移动网络
     *
     * @param context application
     * @return 是否移动网络
     */
    public static boolean isMobileConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (manager != null) {
            networkInfo = manager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }

        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 获取网络状态 2G,3G,WIFI等
     *
     * @param context Activity的上下文或者应用程序的上下文
     * @return 返回值为int类型 取值有1，2，3，4 返回值说明： 1-3G； 2-2G； 3-WIFI; 4-无连接或者其他连接
     */
    public static int getNetState(Context context) {
        ConnectivityManager connect = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobile = null;
        State wifi = null;
        if (connect != null) {
            mobile = connect.getNetworkInfo(
                    ConnectivityManager.TYPE_MOBILE).getState();
            wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .getState();
        }

        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            return 3;// wifi
        }
        if (mobile == State.CONNECTED || mobile == State.CONNECTING) {
            NetworkInfo info = connect.getActiveNetworkInfo();
            if (info != null) {
                if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (info.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS
                            || info.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
                            || info.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE) {
                        return 2;// 2G网络
                    }
                } else {
                    return 1;// 3g
                }
            }
        }
        return 4;// Other
    }

    /**
     * 查看GPS是否开启
     *
     * @param context application
     * @return 是否开启
     */
    public static boolean isGPSOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /***
     * 判断蓝牙是否开启
     * @return true-开启 false-未开启
     */
    public static boolean isBluetoothOpen() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (null == bluetoothAdapter) {
            Log.d(TAG, "该手机没有蓝牙");
            return false;
        }

        return bluetoothAdapter.isEnabled();
    }
}
