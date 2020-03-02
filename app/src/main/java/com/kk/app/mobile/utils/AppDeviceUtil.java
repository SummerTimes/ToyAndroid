package com.kk.app.mobile.utils;

import android.content.Context;

import com.kk.app.mobile.constant.AppConstant;
import com.kk.app.util.SharedPreferenceUtil;


/**
 * @author kk
 * @datetime: 2019-04-26
 * @desc:
 */
public class AppDeviceUtil {

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetWorkType(Context context) {
        return SharedPreferenceUtil.getStringValueFromSP(context, AppConstant.APP_NET_WORK, AppConstant.NET_TYPE, "");
    }

    /**
     * 设置网络类型
     *
     * @param context
     * @param type
     */
    public static void setNetWorkType(Context context, String type) {
        SharedPreferenceUtil.setStringDataIntoSP(context, AppConstant.APP_NET_WORK, AppConstant.NET_TYPE, type);
    }

}
