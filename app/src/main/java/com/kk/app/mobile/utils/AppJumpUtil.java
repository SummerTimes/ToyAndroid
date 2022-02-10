package com.kk.app.mobile.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.kk.app.mobile.MainActivity;

/**
 * @author kk
 * @datetime: 2020/9/1
 * @des #说明
 */
public class AppJumpUtil {

    public static void startMainAvy(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 登录
     *
     * @param context
     */
//    fun onStartLogin(context: Context?) {
//        CC.obtainBuilder("loginComponent")
//                .setContext(context)
//                .setActionName("LoginActivity")
//                .addParam("param", "登陆/模块")
//                .build()
//                .callAsync { cc, result -> Log.e("xp", "登陆$result") }
//    }

    /**
     * 个人中心
     *
     * @param context
     */
//    fun onStartMine(context: Context?) {
//        CC.obtainBuilder("mineComponent")
//                .setContext(context)
//                .setActionName("MineActivity")
//                .addParam("param", "个人中心/模块")
//                .build()
//                .callAsync { cc, result -> Log.e("xp", "个人中心$result") }
//    }

    /**
     * 打开WebView
     */
//    fun onStartWebView(context: Context?) {
//        val jsonObject = JSONObject()
//        try {
//            jsonObject.put("title", "商家客服")
//            jsonObject.put("noTitle", "noTitle")
//            jsonObject.put("url", "http://summertimes.top/2013/06/13/%E6%90%AD%E5%BB%BA:%E5%8D%9A%E5%AE%A2/")
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }
//        CC.obtainBuilder("WebComponent")
//                .setActionName("startWeb")
//                .addParam("param", jsonObject)
//                .setContext(context)
//                .build()
//                .callAsync { cc, result -> Log.e("xp", "---------WebView--------$result") }
//    }
}
