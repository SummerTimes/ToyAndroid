package com.kk.app.login.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kk.app.lib.widget.NoDoubleClickListener;
import com.kk.app.lib.widget.neterror.NeterrorLayout;
import com.kk.app.login.R;
import com.kk.app.util.NetUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
public class WebActivity extends AppCompatActivity {

    public static final String PARAMS = "params";
    public static final String URL = "url";
    public static final String TITLE = "title";
    public static final String HIDE_TITLE = "hide_title";
    public static final String TAG = "xp";

    public NeterrorLayout neterror;
    public RelativeLayout rltTitle;
    public TextView tvTitle;
    public ImageView ivBack;
    public WebView webContent;
    public String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initView();
        initListener();
        initVariables();
        checkNet();
    }

    private void initView() {
        webContent = findViewById(R.id.web_content);
        rltTitle = findViewById(R.id.rlt_title);
        tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_back);
        neterror = findViewById(R.id.nel_error);
        initialWebSetting();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initialWebSetting() {
        final WebSettings settings = webContent.getSettings();
        settings.setJavaScriptEnabled(true);
        //setCacheMode，有网络就是使用默认缓存策略
        if (!NetUtil.checkNet(this)) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        } else {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            webContent.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置可以使用localStorage
        settings.setDomStorageEnabled(true);
        // 应用可以有缓存
        String appCacheDir = this.getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(appCacheDir);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSaveFormData(false);
        settings.setBuiltInZoomControls(false);
    }

    private void initListener() {
        ivBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                onBack();
            }
        });
    }

    protected void initVariables() {
        String params = getIntent().getStringExtra(PARAMS);
        try {
            JSONObject data = new JSONObject(params);
            mUrl = data.optString(URL);
            tvTitle.setText(data.optString(TITLE));
            boolean isHideTitle = data.optBoolean(HIDE_TITLE);

            if (isHideTitle) {
                rltTitle.setVisibility(View.GONE);
            } else {
                rltTitle.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkNet() {
        if (!NetUtil.checkNet(this)) {
            noNet();
        } else {
            hasNet();
        }
    }

    private void noNet() {
        neterror.onTimeoutError();
        webContent.setVisibility(View.GONE);
    }

    private void hasNet() {
        webContent.setVisibility(View.VISIBLE);
        neterror.onConnected();

        webContent.setWebChromeClient(new WebChromeClient());
        webContent.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri parse = Uri.parse(url);
                String scheme = parse.getScheme();
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!TextUtils.isEmpty(webContent.getTitle()) && !webContent.getTitle().contains("http")) {
                    tvTitle.setText(webContent.getTitle());
                }
            }
        });

        if (!TextUtils.isEmpty(mUrl) && webContent != null) {
            webContent.loadUrl(mUrl);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void onBack() {
        if (webContent != null) {
            if (webContent.canGoBack()) {
                webContent.goBack();
            } else {
                onBackPressed();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webContent != null) {
            webContent.clearCache(true);
        }
    }

}
