package com.kk.app.webview.title;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

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
public final class ECardTitle extends BaseTitle {

    private static String CHANGE_STATE = "BLElectronCard" + "#" + "exchangeState";

    private BridgeWebView mBridgeWebView;
    private String mType = BACH_MANAGER;

    private static final String BACH_MANAGER = "1";

    private static final String CANCEL = "2";
    private View iv_back;
    private View selectAll;
    private TextView right;


    public ECardTitle(Activity activity) {
        super(activity);
    }

    @Override
    protected final int getLayout() {
        return R.layout.bl_web_title_ec_card;
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);
        this.mBridgeWebView = bridgeWebView;

        bridgeWebView.registerFunction(ECardTitle.CHANGE_STATE, new INativeCallBack() {
            @Override
            public void onCall(String method, String data, String url, IJSCallFunction ijsCallFunction) {
                try {
                    JSONObject jsonObject = new JSONObject(data);

                    if (!jsonObject.isNull("changeState")) {
                        String type = jsonObject.getString("changeState");
                        switch (type) {
                            case "0":
                                right.setText(bridgeWebView.getContext().getResources().getString(R.string.cancel));
                                selectAll.setVisibility(View.INVISIBLE);
                                mType = CANCEL;
                                break;
                            case "1":
                                right.setText(bridgeWebView.getContext().getResources().getString(R.string.batch_manager));
                                right.setVisibility(View.VISIBLE);
                                mType = BACH_MANAGER;
                                break;
                            case "2":
                                selectAll.setVisibility(View.VISIBLE);
                                right.setText(bridgeWebView.getContext().getResources().getString(R.string.cancel));
                                mType = CANCEL;
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onSetTitle(String data) {

    }

    @Override
    public void initOnCreateTitle() {

        iv_back = findView(R.id.iv_back);
        selectAll = findView(R.id.select_all);
        right = (TextView) findView(R.id.right);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null) getActivity().finish();
            }
        });
//
//
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBridgeWebView.callJs("window.fullSelect");
            }
        });
//
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mType) {
                    case CANCEL:
                        mBridgeWebView.callJs("window.cancleSelect");
                        selectAll.setVisibility(View.INVISIBLE);
                        break;
                    case BACH_MANAGER:
                        mBridgeWebView.callJs("window.manageSelect");
                        selectAll.setVisibility(View.VISIBLE);
                        break;
                }


            }
        });

    }
}
