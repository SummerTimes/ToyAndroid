package com.kk.app.webview.title;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;


import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;

import com.kk.app.web.bljsbridge.BridgeWebView;
import com.kk.app.web.bljsbridge.IJSCallFunction;
import com.kk.app.web.bljsbridge.INativeCallBack;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public abstract class BaseTitle implements ITitle {

    private View mView;
    protected String mMethodUrl;
    protected IJSCallFunction mCallFunction;
    private final WeakReference<Activity> mActivity;
    private final Stack<String> mTitleStack = new Stack<>();
    String SET_TITLE = "title" + "#" + "title";

    public BaseTitle(Activity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    @Override
    public final View createTitle(Context context, ViewGroup parent) {
        if (mView == null) {
            mView = LayoutInflater.from(context).inflate(getLayout(), parent, false);
            initOnCreateTitle();
        }
        parent.removeAllViews();
        parent.addView(mView);
        return mView;
    }

    protected abstract void initOnCreateTitle();

    protected abstract @LayoutRes
    int getLayout();

    protected abstract void onSetTitle(String data);

    @CallSuper
    @Override
    public void registerFunction(BridgeWebView bridgeWebView) {
        bridgeWebView.registerFunction(SET_TITLE, new INativeCallBack() {
            @Override
            public void onCall(String method, String data, String url, IJSCallFunction ijsCallFunction) {
                Log.e("xp", "设置头布局");
                if (!TextUtils.isEmpty(data)) {
                    data = data.substring(1, data.length() - 1);
                }
                mTitleStack.push(data);
                onSetTitle(data);
            }
        });
    }

    @Override
    public boolean onGoBack() {
        if (mTitleStack.isEmpty()) {
            return false;
        }
        mTitleStack.pop();
        if (mTitleStack.isEmpty()) {
            return false;
        }
        onSetTitle(mTitleStack.pop());
        return true;
    }

    public void goBack(BridgeWebView webview) {
        if (webview.canGoBack()) {
            if (!mTitleStack.isEmpty()) {//把当前的移除
                mTitleStack.pop();
            }
            if (!mTitleStack.isEmpty()) {
                onSetTitle(mTitleStack.pop());
            }
            // 返回上一页面
            webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webview.goBack();
        } else {
            onBackPressed();
        }

    }

    protected void finish() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    public Activity getActivity() {
        if (mActivity == null) {
            return null;
        }
        return mActivity.get();
    }

    protected View findView(int id) {
        return mView.findViewById(id);
    }

    protected void onBackPressed() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void setTitle(String title) {
        onSetTitle(title);
    }

    @Override
    public void setInitData(IJSCallFunction callFunction, String url) {
        this.mCallFunction = callFunction;
        this.mMethodUrl = url;
    }
}
