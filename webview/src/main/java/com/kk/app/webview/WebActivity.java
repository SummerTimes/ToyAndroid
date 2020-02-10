package com.kk.app.webview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.kk.app.web.bljsbridge.BridgeWebView;
import com.kk.app.web.vassonic.SonicRuntimeImpl;
import com.kk.app.web.vassonic.SonicSessionClientImpl;
import com.kk.app.webview.title.ITitle;
import com.kk.app.webview.title.WebTitleFactory;
import com.kk.app.webview.utils.NetUtils;
import com.klcw.app.web.R;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Stack;
import java.util.logging.Logger;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class WebActivity extends AppCompatActivity {

    private @Nullable
    ITitle mITitle;
    private CardView cardView;

    private String mNoNeedTitle;
    boolean mIsFirstIn = true;

    private String mUrl;
    private String mTitle;
    private String params = null;

    private SonicSession sonicSession;
    private FunctionRegister mRegister;
    private BridgeWebView bridgeWebView;
    private SonicSessionClientImpl sonicSessionClient;

    private static final String PARAM = "params";
    private Stack<ITitle> mTitleStack = new Stack<>();

    /**
     * start WebActivity
     *
     * @param context
     * @param params
     * @return
     */
    public static boolean startAct(Context context, String params) {
        if (!TextUtils.isEmpty(params)) {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra(PARAM, params);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        initVariables();
        if (0 == ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initSonic();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 701);
        }
        setContentView(R.layout.bl_web_act);
        initView();
        setWeb();
        registerFunction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRegister.onResume(this);
        if (!mIsFirstIn) {
            bridgeWebView.callJs("window.currentPageReload");
        }
        mIsFirstIn = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRegister.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
            if (null != sonicSessionClient) {
                sonicSessionClient.destroy();
                sonicSessionClient = null;
            }
        }
        super.onDestroy();
        mRegister.onDestroy(this);
    }

    @Override
    public void onBackPressed() {
        if (mITitle != null) {
            mITitle.onGoBack();
        }
        goBack(bridgeWebView);
    }

    /**
     * 初始化数据
     */
    protected void initVariables() {
        Intent intent = getIntent();
        if (intent != null) {
            params = intent.getStringExtra(PARAM);
            try {
                JSONObject jsonObject = new JSONObject(params);
                if (!jsonObject.isNull("url")) {
                    mUrl = jsonObject.getString("url");
                }
                if (!jsonObject.isNull("title")) {
                    mTitle = jsonObject.getString("title");
                }
                mNoNeedTitle = jsonObject.optString("noTitle");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    void initSonic() {
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new SonicRuntimeImpl(getApplication()), new SonicConfig.Builder().build());
        }
        sonicSession = SonicEngine.getInstance().createSession(mUrl, new SonicSessionConfig.Builder().setSupportLocalServer(true).build());
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
            Log.i("Webview", "create sonic session fail!");
        }
    }

    private void initView() {
        cardView = findViewById(R.id.title_container);
        bridgeWebView = findViewById(R.id.web);
        bridgeWebView.setSonicSession(sonicSession);
        if (TextUtils.equals("noTitle", mNoNeedTitle)) {
            cardView.setVisibility(View.GONE);
        } else {
            mITitle = WebTitleFactory.produceWebTitle(mUrl, this, bridgeWebView);
            mITitle.createTitle(this, cardView);
            mITitle.registerFunction(bridgeWebView);
            if (!TextUtils.isEmpty(mTitle) && mITitle != null) {
                mITitle.setTitle(mTitle);
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWeb() {
        final WebSettings settings = bridgeWebView.getSettings();
        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + " klcw");
        settings.setJavaScriptEnabled(true);
        if (!NetUtils.checkNet(this)) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            bridgeWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        // 设置可以使用localStorage
        settings.setDomStorageEnabled(true);
        // 应用可以有缓存
        String appCaceDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCaceDir);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(false);
        settings.setBuiltInZoomControls(false);

        //启用数据库
        settings.setDatabaseEnabled(true);
        //设置定位的数据库路径
        String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        settings.setGeolocationDatabasePath(dir);
        //启用地理定位
        settings.setGeolocationEnabled(true);

        if (!TextUtils.isEmpty(mUrl)) {
            mUrl = mUrl.replaceAll(" ", "");
        }
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(bridgeWebView);
            sonicSessionClient.clientReady();
        } else {
            bridgeWebView.loadUrl(mUrl);
        }
    }

    private void registerFunction() {
        mRegister = new FunctionRegister();
        mRegister.onActivityAttach(this);
        mRegister.registerFunction(bridgeWebView, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mRegister.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRegister != null) {
            mRegister.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void goBack(BridgeWebView webView) {
        if (webView.canGoBack()) {
            // 返回上一页面
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            webView.goBack();
            if (mITitle != null && !mITitle.onGoBack()) {
                if (!mTitleStack.isEmpty()) {
                    mTitleStack.pop();
                }
                if (!mTitleStack.isEmpty()) {
                    mITitle = mTitleStack.pop();
                    mITitle.createTitle(WebActivity.this, cardView);
                    mTitleStack.push(mITitle);
                }
            }
        } else {
            finish();
        }
    }
}














