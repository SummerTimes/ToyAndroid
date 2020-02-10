package com.kk.app.webview.title;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponentCallback;
import com.kk.app.web.bljsbridge.BridgeWebView;
import com.klcw.app.web.R;

import org.json.JSONObject;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class StoreTitle extends BaseTitle {

    private View title_btn_left;
    private TextView title_content;
    private BridgeWebView mBridgeWebView;

    public StoreTitle(Activity activity) {
        super(activity);
    }

    @Override
    protected void initOnCreateTitle() {
        title_btn_left = findView(R.id.title_btn_left);
        title_content = (TextView) findView(R.id.title_content);

        title_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CC.obtainBuilder("SelectAddressComponent")
                        .setContext(getActivity())
                        .setActionName("showSelectAddress")
                        .build()
                        .callAsync(new IComponentCallback() {
                            @Override
                            public void onResult(CC cc, CCResult ccResult) {
                                getActivity().finish();
                                if (mBridgeWebView != null) {
                                    JSONObject ccResultData = ccResult.getData();
                                    String latitude = ccResultData.optString("latitude");
                                    String longitude = ccResultData.optString("longitude");
                                    String address = ccResultData.optString("address");
                                    ;
                                    try {
//                                        JSONObject jsonObject = new JSONObject();
//                                        jsonObject.put("url", NetworkConfig.getH5Url() + "#/nearby?lat=" + latitude + "&lon= " + longitude);
//                                        jsonObject.put("address", address);
//                                        CC.obtainBuilder("WebComponent")
//                                                .setActionName("startWeb")
//                                                .setContext(getActivity())
//                                                .setParams(jsonObject)
//                                                .build().callAsync();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });

            }
        });
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);
        this.mBridgeWebView = bridgeWebView;
        title_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(bridgeWebView);
            }
        });

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String params = intent.getStringExtra("params");
            try {
                JSONObject jsonObject = new JSONObject(params);
                String address = jsonObject.optString("address");
                title_content.setText(address);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.bl_web_store_title;
    }

    @Override
    protected void onSetTitle(String data) {
        if (!TextUtils.isEmpty(data)) {
            title_content.setText(data);
        }

    }
}
