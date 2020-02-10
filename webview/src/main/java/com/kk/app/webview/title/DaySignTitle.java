package com.kk.app.webview.title;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kk.app.web.bljsbridge.BridgeWebView;
import com.klcw.app.web.R;

import java.util.logging.Logger;


/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public final class DaySignTitle extends BaseTitle {

    private BridgeWebView mBridgeWebView;
    private TextView tv_right;
    private TextView tv_txt;

    public DaySignTitle(Activity activity) {
        super(activity);
    }

    @Override
    protected final int getLayout() {
        return R.layout.bl_web_title_day_sign;
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);
        this.mBridgeWebView = bridgeWebView;
    }

    @Override
    protected void onSetTitle(String data) {
        tv_txt.setText(data);
    }

    @Override
    public void initOnCreateTitle() {

        findView(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null) {
                    goBack(mBridgeWebView);
                }
            }
        });
        tv_right = (TextView) findView(R.id.tv_right);
        tv_txt = (TextView) findView(R.id.tv_txt);

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBridgeWebView.callJs("BLAlertSignConfirm");
            }
        });
    }

    @Override
    public void setTitle(String title) {
        onSetTitle(title);
    }
}
