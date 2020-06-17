package com.kk.app.web.bljsbridge;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionClient;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class BridgeWebViewClient extends WebViewClient {

    private IWebClientCallBack mCallBack;
    private final BridgeWebView webView;
    private boolean mAlreadyPutIn = true;
    private WebViewClient customClient;
    private WeakReference<SonicSession> wrfSonicSession;


    public BridgeWebViewClient(BridgeWebView webView, IWebClientCallBack callBack) {
        this.webView = webView;
        this.mCallBack = callBack;
    }

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    private static final String TAG = "BridgeWebViewClient";

    public void setSonicSession(SonicSession session){
        wrfSonicSession = new WeakReference<>(session);
    }

    private SonicSessionClient getSessionClient(){
        return (wrfSonicSession!=null && wrfSonicSession.get()!=null) ? wrfSonicSession.get().getSessionClient() : null;
    }

    @TargetApi(21)
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return shouldInterceptRequest(view, request.getUrl().toString());
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        SonicSessionClient client = getSessionClient();
        if(client != null){
            client.requestResource(url);
        }
        return null;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.contains("startMethod")) {
            webView.callJs("window.CTJSBridge.flatMethod");
            return true;
        } else if (url.startsWith(BridgeConfig.API_BRIDGE_HEADER)) {
            webView.dispatch(url);
            return true;
        } else if (url.startsWith(BridgeConfig.METHOD_BRIDGE_HEADER)) { // 如果是返回数据
            webView.dispatch(url);

            webView.callJs("window.CTJSBridge.flatMethod");
            return true;
        }
        if (webView.onChangeUrl(url)) {
            return true;
        }
        if (webView.onPageStarted(url)) {
            webView.stopLoading();
            return true;
        }
        if (!url.startsWith("http")) {
            webView.stopLoading();
            schemeJump(view, url);
            return true;
        }

        mAlreadyPutIn = false;
        if (customClient != null) {
            customClient.shouldOverrideUrlLoading(webView, url);
        }

        return super.shouldOverrideUrlLoading(view, url);
    }


    private void schemeJump(WebView view, String url) {
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (in.resolveActivity(webView.getContext().getPackageManager()) == null) {
            //说明系统中不存在这个activity
            view.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(webView.getContext(), "应用未安装", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            startActivity(in);
        }
    }

    private void startActivity(Intent in) {
        try {//fixed 非法scheme
            webView.getContext().startActivity(in);
        }catch (Exception ex){}
    }


    @Override
    public void onPageStarted(final WebView view, String url, Bitmap favicon) {
        if (!url.startsWith("http") && !webView.onPageStarted(url)) {
            webView.stopLoading();
            schemeJump(view, url);
            return;
        }
        if (!webView.onPageStarted(url)) {
            super.onPageStarted(view, url, favicon);
        } else {
            webView.stopLoading();
        }

        if (customClient != null) {
            customClient.onPageStarted(view, url, favicon);
        }
        SonicSessionClient client = getSessionClient();
        if(client != null){
            client.pageFinish(url);
        }
    }


    public void setAlreadyPutIn(boolean mAlreadyPutIn) {
        this.mAlreadyPutIn = mAlreadyPutIn;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (!mAlreadyPutIn) {
            mAlreadyPutIn = true;
            BridgeUtil.webViewLoadLocalJs(view, BridgeWebView.toLoadJs);
        }

        if (customClient != null) {
            customClient.onPageFinished(view, url);
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();// Ignore SSL certificate errors
    }


    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (customClient != null) {
            customClient.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    void setCustomClient(WebViewClient customClient) {
        this.customClient = customClient;
    }
}