package com.kk.app.web.bljsbridge;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tencent.sonic.sdk.SonicSession;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
@SuppressLint("SetJavaScriptEnabled")
public class BridgeWebView extends WebView {

    private final String TAG = "BridgeWebView";

    public static final String toLoadJs = "CTJSBridge.js";

    final static String CALL_JS = "javascript:%s();";

    final static String CALL_JS1 = "javascript:%s(%s);";

    private OnUrlChangeListener mListener;


    private PageCall pageCall;
    private JsChromeClient mJsChromeClient;
    private WebChromeClient mCustomHandleClient;
    private BridgeWebViewClient mBridgeWebViewClient;
    private IJSBridgeMediator mIJsMediator;
    private WebViewClient mCustomBridgeClient;

    public BridgeWebView(Context context) {
        super(context);
        init();
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    public void loadNewUrl(String newUrl) {
        loadUrl(newUrl);
    }


    private void init() {
        this.setVerticalScrollBarEnabled(false);
        this.setHorizontalScrollBarEnabled(false);
        this.getSettings().setJavaScriptEnabled(true);

        this.removeJavascriptInterface("searchBoxJavaBridge_");
        this.removeJavascriptInterface("accessibility");
        this.removeJavascriptInterface("ccessibilityaversal");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        mJsChromeClient = generateBridgeWebViewChromeClient();

        mBridgeWebViewClient = generateBridgeWebViewClient(mJsChromeClient);

        this.setWebViewClient(mBridgeWebViewClient);

        this.setWebChromeClient(mJsChromeClient);
    }



    private JsChromeClient generateBridgeWebViewChromeClient() {
        return new JsChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mCustomHandleClient != null) {
                    mCustomHandleClient.onProgressChanged(view, newProgress);
                }
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
                if (mCustomHandleClient != null) {
                    mCustomHandleClient.onReceivedIcon(view, icon);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mCustomHandleClient != null) {
                    mCustomHandleClient.onReceivedTitle(view, title);
                }
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mCustomHandleClient != null) {
                    return mCustomHandleClient.onShowFileChooser(webView, filePathCallback, fileChooserParams);
                }
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }


            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                if (mCustomHandleClient != null) {
                    mCustomHandleClient.onGeolocationPermissionsShowPrompt(origin, callback);
                } else {
                    super.onGeolocationPermissionsShowPrompt(origin, callback);
                }
            }
        };
    }


    protected BridgeWebViewClient generateBridgeWebViewClient(IWebClientCallBack callBack) {
        return new BridgeWebViewClient(this, callBack);
    }

    public void setSonicSession(SonicSession session){
        if(mBridgeWebViewClient != null && session!=null){
            mBridgeWebViewClient.setSonicSession(session);
        }
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        if (client instanceof BridgeWebViewClient) {
            this.mBridgeWebViewClient= (BridgeWebViewClient) client;
            super.setWebViewClient(client);
            return;
        }
        this.mCustomBridgeClient = client;
        if(mBridgeWebViewClient!=null){
            mBridgeWebViewClient.setCustomClient(mCustomBridgeClient);
        }
    }

    /**
     * 注册方法给JS调用
     *
     * @param methodName
     * @param callFunction
     * @return
     */
    public BridgeWebView registerFunction(String methodName, INativeCallBack callFunction) {
        if (mIJsMediator == null) {
            mIJsMediator = new DefaultJsBridgeMediator(this);
        }
        ArrayMap<String, INativeCallBack> functionContainer = mIJsMediator.produceFunctionContainer();
        if (functionContainer == null) {
            Log.e("BridgeWebView", "中介需要提供非空的容器");
            return this;
        }
        mIJsMediator.registerFunction(methodName,callFunction);
        return this;
    }

    @Override
    public void setWebChromeClient(final WebChromeClient client) {
        if (client instanceof JsChromeClient) {
            super.setWebChromeClient(mJsChromeClient);
            return;
        }
        this.mCustomHandleClient = client;
    }


    /**
     * 注册方法给JS调用
     *
     * @param callFunction
     * @param methodName
     */
    public void registerFunction(INativeCallBack callFunction, String... methodName) {
        for (String method : methodName) {
            registerFunction(method, callFunction);
        }
    }

    public void registerMediator(IJSBridgeMediator ijsBridgeMediator) {
        this.mIJsMediator = ijsBridgeMediator;
    }

    public void dispatch(String urls) {
        if (mIJsMediator == null) {
            mIJsMediator = new DefaultJsBridgeMediator(this);
        }
        mIJsMediator.onDispatch(urls);
    }

    public boolean onChangeUrl(String url) {
        if (mListener != null) {
            return mListener.onChangeUrl(url);
        }
        return false;
    }


    public void registerOnUrlChangeListener(OnUrlChangeListener listener) {
        this.mListener = listener;
    }

    public boolean onPageStarted(String url) {
        if (pageCall != null) {
            pageCall.onPageStarted(url);
        }
        if (mListener != null) {
            return mListener.onPageStarted(url);
        }
        return false;
    }

    public void setPageCall(PageCall call) {
        pageCall = call;
    }

    public void onPageFinished(String url) {
        if (pageCall != null) {
            pageCall.onPageFinished(url);
        }
    }


    /**
     * 调用JS
     *
     * @param js
     */
    public void callJs(final String js) {
        BridgeWebView.this.post(new Runnable() {
            @Override
            public void run() {
                String command = String.format(CALL_JS, js);
                loadUrl(command);
            }
        });

    }

    public void callJs(final String name, final String arguments) {
        BridgeWebView.this.post(new Runnable() {
            @Override
            public void run() {
                String command = String.format(CALL_JS1, name, arguments);
                loadUrl(command);
            }
        });

    }
}
