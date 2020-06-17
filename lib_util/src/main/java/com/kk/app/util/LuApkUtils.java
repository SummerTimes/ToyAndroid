package com.kk.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;

/**
 * 获取apk相关信息
 */
public class LuApkUtils {

    /***
     * 获取当前App的VersionCode
     * @return versionCode   0获取失败
     */
    public static int getCurrentVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /***
     * 获取当前App的VersionName
     * @param context 当前上下文
     * @return
     */
    public static String getCurrentVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取APK的icon
     * @param context 当前上下文
     * @param apkPath apk在SD中的路径
     * @return
     */
    public static Drawable getApkIcon(Context context, String apkPath) {

        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;
            return appInfo.loadIcon(pm);
        }
        return null;
    }

    /**
     * 获取已安装app签名
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getInstalledApkSignature(Context context,
                                                  String packageName) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm
                .getInstalledPackages(PackageManager.GET_SIGNATURES);

        Iterator<PackageInfo> iter = apps.iterator();
        while (iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String thisName = packageinfo.packageName;
            if (thisName.equals(packageName)) {
                return packageinfo.signatures[0].toCharsString();
            }
        }

        return null;
    }

    /**
     * 获取已安装的应用的签名信息的MD5和SHA字符串
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String[] getInstalledApkMD5SHA(Context context,
                                                 String packageName) {
        // 存储MD5串和SHA串
        String[] strArr = new String[2];

        try {
            PackageManager pm = context.getPackageManager();
            // 获取签名信息
            @SuppressLint("PackageManagerGetSignatures") PackageInfo packageInfo = pm.getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sign.toByteArray());

            byte[] md5Digest = md.digest();
            // MD5串
            String md5DigestStr = LuStringUtils.bytesToHexString(md5Digest);

            MessageDigest md2 = MessageDigest.getInstance("SHA1");
            md2.update(sign.toByteArray());
            byte[] shaDigest = md2.digest();
            // SHA串
            String shaDigestStr = LuStringUtils.bytesToHexString(shaDigest);

            strArr[0] = md5DigestStr;
            strArr[1] = shaDigestStr;

            return strArr;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strArr;
    }
}
