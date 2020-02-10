package com.kk.app.web.function.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.kk.app.web.bljsbridge.BridgeWebView;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface IFunction {

    void registerFunction(BridgeWebView webView, Context context);

    void onActivityAttach(Activity activity);

    void onResume(Activity activity);

    void onDestroy(Activity activity);

    void onReceive(String msg);

    void onPause(Activity activity);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
