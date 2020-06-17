package com.kk.app.mobile.utils;

import android.content.Context;
import android.text.TextUtils;

import com.kk.app.mobile.constant.AppConstant;
import com.kk.app.util.LuPreferenceUtil;
import com.meituan.android.walle.WalleChannelReader;


/**
 * @author kk
 * @datetime: 2019-04-26
 * @desc:
 */
public class AppDeviceUtil {

    /**
     * 获取渠道号 获取不到默认官网
     *
     * @param context
     */
    public static String getChannelNo(Context context) {
        String channelNo = WalleChannelReader.getChannel(context);
        return !TextUtils.isEmpty(channelNo) ? channelNo : "kk_Home";
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static String getNetWorkType(Context context) {
        return LuPreferenceUtil.getStringValueFromSP(context, AppConstant.APP_NET_WORK, AppConstant.NET_TYPE, "");
    }

    /**
     * 设置网络类型
     *
     * @param context
     * @param type
     */
    public static void setNetWorkType(Context context, String type) {
        LuPreferenceUtil.setStringDataIntoSP(context, AppConstant.APP_NET_WORK, AppConstant.NET_TYPE, type);
    }

}
