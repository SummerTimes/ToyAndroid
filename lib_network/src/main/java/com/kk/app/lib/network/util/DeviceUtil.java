package com.kk.app.lib.network.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author kk
 * @datetime: 2020/4/24
 * @desc:
 */
public class DeviceUtil {

    private static final List<String> M_INVALID_ANDROID_ID = new ArrayList<String>() {
        {
            this.add("9774d56d682e549c");
            this.add("0123456789abcdef");
        }
    };

    public static String getDeviceId(Context mContext) {
        String deviceId = getAndroidID(mContext);
        if (!isValidAndroidId(deviceId)) {
            deviceId = UUID.randomUUID().toString().replaceAll("-", "");
        }
        return deviceId;
    }

    @SuppressLint("HardwareIds")
    private static String getAndroidID(Context mContext) {
        String androidID = "";
        try {
            androidID = Settings.Secure.getString(mContext.getContentResolver(), "android_id");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
        return androidID;
    }

    private static boolean isValidAndroidId(String androidId) {
        return !TextUtils.isEmpty(androidId) && !M_INVALID_ANDROID_ID.contains(androidId.toLowerCase());
    }
}
