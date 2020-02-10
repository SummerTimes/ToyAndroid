package com.kk.app.webview.title;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kk.app.lib.widget.NoDoubleClickListener;
import com.kk.app.web.bljsbridge.BridgeWebView;
import com.kk.app.web.bljsbridge.IJSCallFunction;
import com.kk.app.web.bljsbridge.INativeCallBack;
import com.klcw.app.web.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class CmsTitle extends BaseTitle {

    private TextView titleContent;
    private View back;
    private View cart;
    private View share;
    private String SHOW_SHARE = "BLCMSWebViewController" + "#" + "setNeedShowShareButton";

    public CmsTitle(Activity activity) {
        super(activity);
    }

    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);
        bridgeWebView.registerFunction(SHOW_SHARE, new INativeCallBack() {

            @Override
            public void onCall(String method, String data, String url, IJSCallFunction ijsCallFunction) {
                try {
                    JSONObject json = new JSONObject(data);
                    String needShowShare = json.optString("needShowShare");
                    if (TextUtils.equals("1", needShowShare)) {
                        share.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(bridgeWebView);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null) {
                    /*CC.obtainBuilder("CartComponent")
                            .setActionName("jumpCartActivity")
                            .setContext(activity)
                            .build().callAsync();*/
                }
            }
        });
        share.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                bridgeWebView.callJs("window.shareCmsPageInApp");
            }
        });
    }

    @Override
    protected void initOnCreateTitle() {
        titleContent = (TextView) findView(R.id.tv_txt);
        back = findView(R.id.iv_back);
        cart = findView(R.id.right);
        share = findView(R.id.share_in_title);
    }

    @Override
    protected int getLayout() {
        return R.layout.bl_web_cms_title;
    }

    @Override
    protected void onSetTitle(String data) {
        titleContent.setText(data);
    }
}
