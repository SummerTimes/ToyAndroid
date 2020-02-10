package com.kk.app.lib.network;


import androidx.annotation.NonNull;

import com.billy.cc.core.component.CCResult;
import com.kk.app.lib.interceptor.TypeToken;


/**
 * Gson方式的callback
 * @author billy.qi
 * @since 17/8/17 14:03
 */
public abstract class NetworkCallback<T> extends TypeToken {

    /**
     * 成功时回调的方法
     * @param rawResult 组件调用的结果,不会为null
     * @param convertedResult 通过gson转换后的对象（gson转换在异步线程中执行）
     */
    public abstract void onSuccess(@NonNull CCResult rawResult, T convertedResult);

    /**
     * 失败时的回调方法
     * @param result 组件调用的结果
     */
    public abstract void onFailed(@NonNull CCResult result);

    /**
     * 在执行完 {@link #onSuccess(CCResult, Object)} 或 {@link #onFailed(CCResult)} 后执行
     * 此方法一定会被执行（即使onSuccess或onFailed方法报异常）
     * 可以在此方法内处理网络请求的结束事件，例如：关闭加载中的processDialog
     * @param result 组件调用的result
     */
    public abstract void onFinally(@NonNull CCResult result);
}