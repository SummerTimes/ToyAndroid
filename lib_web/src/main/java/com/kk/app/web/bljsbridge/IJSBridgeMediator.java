package com.kk.app.web.bljsbridge;

import android.util.ArrayMap;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface IJSBridgeMediator {

    void onDispatch(String data);

    ArrayMap<String, INativeCallBack> produceFunctionContainer();

    void registerFunction(String key, INativeCallBack callFunction);
}
