package com.kk.app.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 手机相关信息常用工具类
 */
public class PhoneUtil {
    private static final String TAG = PhoneUtil.class.getSimpleName();

    /**
     * 获取手机分辨率
     *
     * @param context
     * @return 宽和高的数组 单位为px 注：返回int数组中，第一个值为宽度，第二个参数为高度
     */
    public static int[] getPhoneResolution(Context context) {
        int[] result = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getMetrics(dm);
        result[0] = dm.widthPixels;
        result[1] = dm.heightPixels;
        return result;

    }

    /**
     * 获取屏幕高度 复用分辨率获取
     *
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        return getPhoneResolution(context)[1];
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        return getPhoneResolution(context)[0];
    }

    /**
     * 获取手机唯一编号——IMEI
     *
     * @param context Activity或者应用的上下文
     * @return 获取手机的唯一编号，如果获取直接返回，如果获取不到返回null
     */
    @SuppressLint("HardwareIds")
    public static String getDeviceId(Context context) {
       /* try {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String phoneImei = telephonyManager.getDeviceId();
            if (!TextUtils.isEmpty(phoneImei)) {
                // 如果存在，直接返回
                return phoneImei;
            } else {
                return Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return null;
        }*/
        return null;
    }

    /**
     * 获取手机的mac地址
     *
     * @param context Activity的上下文或者应用程序的上下文
     * @return 获取正确直接返回，获取不到时返回null
     */
    @SuppressLint("HardwareIds")
    public static String getMacInfo(Context context) {
        String result = null;
        @SuppressLint("WifiManagerPotentialLeak") WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = (wifiManager == null ? null : wifiManager
                .getConnectionInfo());
        if (info != null) {
            result = info.getMacAddress();
        }
        return result;
    }

    /**
     * 获取终端设备的CPU信息
     *
     * @return 返回CPU的基本信息字符串
     */
    public static String getCpuInfo() {
        String str = null;
        FileReader localFileReader = null;
        BufferedReader localBufferedReader = null;
        try {
            localFileReader = new FileReader("/proc/cpuinfo");
            try {
                localBufferedReader = new BufferedReader(localFileReader,
                        1024);
                str = localBufferedReader.readLine();
                localBufferedReader.close();
                localFileReader.close();
            } catch (IOException localIOException) {
                Log.e(TAG, "Could not read from file /proc/cpuinfo");
            }
        } catch (FileNotFoundException localFileNotFoundException) {
            Log.e(TAG, "Could not open file /proc/cpuinfo");

        }
        if (str != null) {
            int i = str.indexOf(58) + 1;
            str = str.substring(i);
        }
        return str;
    }

    /**
     * 实时获取CPU当前频率（单位KHZ）
     *
     * @return
     */
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text;
            if (result.contains(".")) {
                result = result.substring(0, result.indexOf("."));
            }
            long cpufreq = Long.parseLong(result);

            cpufreq = cpufreq / 1000;
            result = cpufreq + " Mhz";
            br.close();
        } catch (FileNotFoundException e) {
            result = "N/A";
            e.printStackTrace();
        } catch (IOException e) {
            result = "N/A";
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断当前SD是否存在
     *
     * @return 返回为boolean值，如果sd卡存在则为true否则为false
     */
    public static boolean existSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取当前的手机号
     *
     * @param context Activity的上下文或者应用程序的上下文
     * @return 返回值为手机号码，如果获取成功返回手机号码，获取失败则返回null
     */
    public static String getPhoneNumber(Context context) {
       /* TelephonyManager tManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String number = tManager.getLine1Number();
        if (StringUtil.isEmpty(number)) {
            return null;
        }
        Log.e(TAG, "所获得的手机号：" + number);
        return number;*/
        return "";
    }

    /**
     * 获取国家标识(当前系统语言)
     *
     * @param mContext Activity的上下文或者应用程序的上下文
     * @return 获取配置国家标识，默认为中国
     */
    public static String getCountry(Context mContext) {
        Configuration curConfiguration = mContext.getResources()
                .getConfiguration();
        if ((curConfiguration != null) && (curConfiguration.locale != null)) {
            return curConfiguration.locale.getCountry();
        }
        return "CN";
    }

    /**
     * 获取运营商国家码信息
     *
     * @param context Activity的上下文或者应用程序的上下文
     * @return 获取sim卡或者uim卡的国家标识，前三位为460代表中国，返回CN，其他返回标识码，未能获取返回null
     */
    @SuppressLint("HardwareIds")
    public static String getSimCardCountry(Context context) {
       /* String countryCode = null;
        String imsi;
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        imsi = telephonyManager.getSubscriberId();
        if (imsi != null && !"".equals(imsi)) {
            String strCountryCode = imsi.substring(0, 3);
            int code = Integer.parseInt(strCountryCode);
            if (code == 460) {
                countryCode = "CN";
            } else {
                countryCode = Integer.toString(code);
            }
        } else {
            Log.i(TAG, "未能获取国家码，可能未装sim卡或者权限不允许");
        }
        return countryCode;*/
       return "";
    }

    /**
     * 获取内核版本
     *
     * @return 返回手机内核版本号信息，类型为String
     */
    public static String getKernelVersion() {
        String phoneProcess = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");

        } catch (IOException e) {
            phoneProcess = "N/A";
        }
        InputStream outs = process.getInputStream();
        InputStreamReader isrout = new InputStreamReader(outs);
        BufferedReader brout = new BufferedReader(isrout, 8 * 1024);
        String result = "";
        String line;
        try {
            while ((line = brout.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            phoneProcess = "N/A";
        }
        if (result != "") {
            String Keyword = "version ";
            int index = result.indexOf(Keyword);
            line = result.substring(index + Keyword.length());
            index = line.indexOf(" ");
            phoneProcess = line.substring(0, line.length());
        }
        return phoneProcess;
    }

    /**
     * 获得当前安卓系统版本
     *
     * @return 返回手机android的当前版本，类型为String
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMode(Context context, View windowToken) {
        ((InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 返回当前系统里面有多少个进程正在运行
     *
     * @param context
     * @return
     */
    public static int getRunningProcessCount(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        return infos.size();
    }

    /**
     * 获取手机的可用内存
     *
     * @param context
     * @return long, 单位为byte
     */
    public static long getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo outInfo = new MemoryInfo();
        am.getMemoryInfo(outInfo);
        return outInfo.availMem; // byte
    }

    /**
     * 获取手机的总内存
     *
     * @param context
     * @return long型内存总量，单位为kb
     */
    public static long getTotalMemory(Context context) {
        try {
            File file = new File("/proc/meminfo");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String info = br.readLine();
            // MemTotal: 513000 kB
            StringBuilder sb = new StringBuilder();
            char[] chararray = info.toCharArray();
            for (char c : chararray) {
                if (c >= '0' && c <= '9') {
                    sb.append(c);
                    Log.i(TAG, "value" + c);
                }
            }
            br.close();
            return Integer.parseInt(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取openAPI的sn
     *
     * @param context
     * @return
     */
    public static String getPhoneInfoHash(Context context) {
        String imei = getDeviceId(context);
        if (imei == null) {
            imei = "";
        }
        String mac = getMacInfo(context);
        if (mac == null) {
            mac = "";
        }
        String cpu = getCpuInfo();
        if (cpu == null) {
            cpu = "";
        }
        return (imei + mac + cpu).hashCode() + "";
    }

}
