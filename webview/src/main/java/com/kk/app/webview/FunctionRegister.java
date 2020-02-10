package com.kk.app.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import com.kk.app.web.bljsbridge.BridgeWebView;
import com.kk.app.web.function.register.IFunction;
import com.kk.app.web.function.register.IFunctionRegisterManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class FunctionRegister implements IFunction {

    static List<IFunctionRegisterManager> mFunctionManagers = new ArrayList<>();

    List<IFunction> mRealFunctions = new ArrayList<>();

    public static void autoRegisterFunction(IFunctionRegisterManager iFunction) {
        mFunctionManagers.add(iFunction);
    }


    @Override
    public void registerFunction(BridgeWebView webView, Context context) {
        for (IFunctionRegisterManager manager : mFunctionManagers) {
            manager.onBind(mRealFunctions);
        }

        for (IFunction realFunction : mRealFunctions) {
            realFunction.registerFunction(webView, context);
        }

    }


    @Override
    public void onActivityAttach(Activity activity) {

        for (IFunction function : mRealFunctions) {
            function.onActivityAttach(activity);
        }

    }

    @Override
    public void onResume(Activity activity) {
        for (IFunction function : mRealFunctions) {
            function.onResume(activity);
        }
    }

    @Override
    public void onDestroy(Activity activity) {
        for (IFunction function : mRealFunctions) {
            function.onDestroy(activity);
        }
    }

    @Override
    public void onReceive(String msg) {
        for (IFunction function : mRealFunctions) {
            function.onReceive(msg);
        }
    }

    @Override
    public void onPause(Activity activity) {
        for (IFunction function : mRealFunctions) {
            function.onPause(activity);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        for (IFunction function : mRealFunctions) {
            function.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (IFunction function : mRealFunctions) {
            function.onActivityResult(requestCode, resultCode, data);
        }
    }
}
