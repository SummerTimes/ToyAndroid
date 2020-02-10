package com.kk.app.web.bljsbridge;

import android.util.ArrayMap;
import android.util.Log;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class DefaultJsBridgeMediator implements IJSBridgeMediator {

    private static final String TAG = "DefaultJsBridgeMediator";
    private final BridgeWebView mBridgeWebView;

    private final ArrayMap<String, INativeCallBack> mFunctionContainer = new ArrayMap<>();

    private final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:CTJSBridge.CallBack('%s','%s','%s');";

    public DefaultJsBridgeMediator(BridgeWebView bridgeWebView) {
        this.mBridgeWebView = bridgeWebView;
    }

    @Override
    public void onDispatch(String urls) {
        Log.e(TAG, "---urls------" + urls);
        String[] urlList = urls.split(BridgeConfig.SPERATE);
        for (String url : urlList) {
            Log.e(TAG, "---url------" + url);
            String[] strings = url.split(BridgeConfig.METHOD_NAME);
            if (strings.length != 2) {
                Log.e(TAG, url + "必须是" + BridgeConfig.METHOD_NAME + "加注册的方法结尾");
                return;
            }
            String method = strings[1];
            Log.e(TAG, "---method------" + method);
            INativeCallBack callNative = mFunctionContainer.get(method);

            String data = null;
            if (url.contains(BridgeConfig.API_BRIDGE_HEADER)) {
                String[] ss = url.split(BridgeConfig.DATA);
                data = ss[1].split(BridgeConfig.API_NAME)[0];
                Log.e(TAG, "--1-data------" + data);
            } else if (url.contains(BridgeConfig.TITLE_BRIDGE_HEADER)) {
                String[] ss = url.split(BridgeConfig.DATA);
                data = ss[1].split(BridgeConfig.METHOD_NAME)[0];
                Log.e(TAG, "--2-data------" + data);
            } else if (url.contains(BridgeConfig.METHOD_BRIDGE_HEADER)) {
                String[] ss = url.split(BridgeConfig.DATA);
                data = ss[1].split(BridgeConfig.TARGET_NAME)[0];
                Log.e(TAG, "--3-data------" + data);
            }

            if (callNative == null) {
                Log.e(TAG, "方法" + method + "未注册使用默认方法");
                callNative = mFunctionContainer.get(BridgeConfig.DEFAULT_METHOD_NAME);
            }
            if (callNative == null) {
                return;
            }
            callNative.onCall(method, data, url, new DefaultIJsCallBack(mBridgeWebView));
        }
    }


    public static class DefaultIJsCallBack implements IJSCallFunction {

        private final BridgeWebView mBridgeWebView;

        public DefaultIJsCallBack(BridgeWebView bridgeWebView) {
            this.mBridgeWebView = bridgeWebView;
        }

        @Override
        public void onCall(JSEntity data, String url) {
            if (data == null) {
                return;
            }
            Log.e(TAG, "------data-----4-----" + data);
            String[] strings = url.split(BridgeConfig.METHOD_NAME);
            Log.e(TAG, "------strings---5-------" + strings);
            if (strings.length != 2) {
                Log.e(TAG, url + "必须是" + BridgeConfig.METHOD_NAME + "加注册的方法结尾");
                return;
            }
            String indentifer;
            String[] ss = url.split(BridgeConfig.DATA);
            Log.e(TAG, "------ss---5-------" + ss.toString());
            indentifer = ss[0].split(BridgeConfig.API_BRIDGE_HEADER)[0].split("=")[1];
            data.data = data.data.replace("\\\\", "\\").replace("\'", "\\'");
            final String jsonData = data.data;
            Log.e(TAG, "------jsonData---6-------" + indentifer);
            Log.e(TAG, "------jsonData---7-------" + jsonData);
            Log.e(TAG, "------jsonData---8-------" + data.status);
            final String javascriptCommand = String.format(JS_HANDLE_MESSAGE_FROM_JAVA, indentifer, data.status, jsonData);
            mBridgeWebView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBridgeWebView.loadUrl(javascriptCommand);
                }
            }, 100);

        }
    }


    @Override
    public ArrayMap<String, INativeCallBack> produceFunctionContainer() {
        return mFunctionContainer;
    }

    @Override
    public void registerFunction(String methodName, INativeCallBack callFunction) {
        mFunctionContainer.put(methodName, callFunction);
    }
}
