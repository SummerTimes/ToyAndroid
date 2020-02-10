package com.kk.app.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;


/**
 * description:单位转换工具类
 * author: mlp00
 * date: 2017/8/23 13:48
 */
public class UnitUtil {

    /***
     * dip或dp:设备独立像素，与设备屏幕有关
     * px:像素 ,就是屏幕上实际的像素点单位
     * sp:类似dp,主要处理文字大小
     * dpi:屏幕像素密度，每英寸多少像素
     */

    private static DisplayMetrics displayMetrics = null;

    static {
        displayMetrics = Resources.getSystem().getDisplayMetrics();
    }

    /***
     * dip(dp)转为px
     * @param value
     * @return
     */
    public static int dip2px(float value) {
        final float scale = displayMetrics.density;
        return (int) (value * scale + 0.5f);
    }

    /***
     * px转为dip(dp)
     * @param value
     * @return
     */
    public static int px2dip(float value) {
        final float scale = displayMetrics.density;
        return (int) (value / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param value
     * @return
     */
    public static int sp2px(float value) {
        float fontScale = displayMetrics.scaledDensity;
        return (int) (value * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param value
     * @return
     */
    public static int px2sp(float value) {
        float fontScale = displayMetrics.scaledDensity;
        return (int) (value / fontScale + 0.5f);
    }
}
