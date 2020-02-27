package com.kk.app.lib.interceptor;

import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.Chain;
import com.billy.cc.core.component.ICCInterceptor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * @author billy.qi
 * @datetime: 2017/08/16
 * @desc:将组件调用CCResult的data转换成JavaBean/使用gson进行转换
 */
public class NetworkGsonInterceptor implements ICCInterceptor {

    /**
     * 对应network组件中保存请求结果的key
     */
    private static final String KEY_RESULT = "result";
    private Gson gson = new Gson();
    private Type type;

    public NetworkGsonInterceptor(TypeToken obj) {
        if (obj != null) {
            try {
                this.type = obj.getType();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CCResult intercept(Chain chain) {
        CCResult ccResult = chain.proceed();
        if (type != null) {
            if (ccResult.isSuccess() && ccResult.getCode() == CCResult.CODE_SUCCESS) {
                try {
                    JSONObject data = ccResult.getData();
                    if (data != null) {
                        String result = data.optString(KEY_RESULT);
                        data.remove(KEY_RESULT);
                        if (type == String.class) {
                            data.put(KEY_RESULT, result);
                        } else if (type == JSONObject.class) {
                            data.put(KEY_RESULT, new JSONObject(result));
                        } else if (type == JSONArray.class) {
                            data.put(KEY_RESULT, new JSONArray(result));
                        } else {
                            Object t = gson.fromJson(result, type);
                            data.put(KEY_RESULT, t);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return ccResult;
    }
}
