package com.kk.app.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 手机相关信息常用工具类
 */
public class LuSizeUtils {

    private static final String TAG = LuSizeUtils.class.getSimpleName();

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
}
