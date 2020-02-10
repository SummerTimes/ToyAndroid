package com.kk.app.webview.title;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.kk.app.web.bljsbridge.BridgeWebView;
import com.klcw.app.web.R;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class BuyListTitle extends BaseTitle {

    private View iv_back;
    private View share_in_title;
    private TextView tv_txt;
    private View focus_button;

    public BuyListTitle(Activity activity) {
        super(activity);
    }

    @Override
    protected void initOnCreateTitle() {
        iv_back = findView(R.id.iv_back);
        share_in_title = findView(R.id.share_in_title);
        tv_txt = (TextView) findView(R.id.tv_txt);
        focus_button = findView(R.id.focus_button);
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null) {
                    activity.onBackPressed();
                }
            }
        });

        focus_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*CC.obtainBuilder(ConstLogin.LOGIN_COMPONENT_NAME)
                        .setActionName(ConstLogin.ACTION_LOGIN_STATUS)
                        .build()
                        .callAsyncCallbackOnMainThread(new IComponentCallback() {
                            @Override
                            public void onResult(CC cc, CCResult ccResult) {
                                if (ccResult.isSuccess()) {
                                    //获取成功处理
                                    JSONObject data = ccResult.getData();
                                    boolean isLogin = data.optBoolean("isLogin");
                                    String userInfo = data.optString(ConstLogin.USER_INFO);
                                    String serviceCfg = data.optString(ConstLogin.SERVICE_CFG);
                                    if (isLogin) {
                                        //已登录状态
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("url",
                                                    UrlUtils.getInstance().getH5() + "#/myFollow");
                                            CC.obtainBuilder("WebComponent")
                                                    .setParams(jsonObject)
                                                    .setActionName("startWeb")
                                                    .setContext(getActivity())
                                                    .build()
                                                    .callAsync();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        //未登录状态
                                        CC.obtainBuilder(ConstLogin.LOGIN_COMPONENT_NAME)
                                                .setContext(getActivity())
                                                .setParams(data)//非必传
                                                .setActionName(ConstLogin.ACTION_LOGIN)
                                                .build()
                                                .callAsync();
                                    }
                                } else {
                                    //获取失败处理
                                }
                            }
                        });*/
            }
        });

        share_in_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bridgeWebView.callJs("window.commShare");
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.bl_web_buy_list_title;
    }

    @Override
    protected void onSetTitle(String data) {
        tv_txt.setText(data);
    }
}
