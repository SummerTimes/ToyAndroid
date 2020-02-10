package com.kk.app.webview.title;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kk.app.web.bljsbridge.BridgeWebView;
import com.kk.app.web.bljsbridge.IJSCallFunction;
import com.kk.app.web.bljsbridge.INativeCallBack;
import com.klcw.app.web.R;

import org.json.JSONObject;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public final class RechargeTitle extends BaseTitle {

    private BridgeWebView mBridgeWebView;
    private String type;
    private View iv_back;
    private TextView tv_right;
    private View ibtn_more;
    private TextView tv_txt;

    String BL_PAYMENTRECORDACTION = "BLChargeAndPayment#setType";//充值记录

    private String mJson;

    public RechargeTitle(Activity activity) {
        super(activity);
    }

    @Override
    protected final int getLayout() {
        return R.layout.bl_web_title_recharge;
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);
        this.mBridgeWebView = bridgeWebView;

        bridgeWebView.registerFunction(BL_PAYMENTRECORDACTION, new INativeCallBack() {
            @Override
            public void onCall(String method, String data, String url, IJSCallFunction ijsCallFunction) {
                setRechargeTitle(data);
            }
        });
    }


    /**
     * 设置充值缴费标题
     *
     * @param param {"type":""}
     */
    private void setRechargeTitle(String param) {
        setAction(param);
    }


    @Override
    protected void onSetTitle(String data) {
        tv_txt.setText(data);
    }

    @Override
    public void initOnCreateTitle() {


        iv_back = findView(R.id.iv_back);
        tv_right = (TextView) findView(R.id.tv_right);
        ibtn_more = findView(R.id.ibtn_more);
        tv_txt = (TextView) findView(R.id.tv_txt);


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null) {
                    goBack(mBridgeWebView);
                }
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mJson)) {
                    /*try {
                        JSONObject jsonObj = new JSONObject(mJson);
                        CC.obtainBuilder("ResourceJumpComponent")
                                .setActionName("resourceJump")
                                .setParams(jsonObj)
                                .build()
                                .callAsync();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }*/
                    return;
                }
                if (getActivity() != null && !TextUtils.isEmpty(type)) {
                    JSONObject data = new JSONObject();
                    try{
                        data.put("id","myorder");
                        data.put("params",String.format("{\"serviceType\":\"%s\",\"tabIndex\":0}", type));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    /*CC.obtainBuilder("ResourceJumpComponent")
                            .setContext(getActivity())
                            .setActionName("pageManager")
                            .setParams(data)
                            .build()
                            .callAsync();*/
                }

               /* if (mTitleBean != null) {
                    JSEntity jsEntity = new JSEntity();
                    jsEntity.data = mTitleBean.getWidgets().get(0).getWidgetIndex();
                    jsEntity.status = BridgeConfig.SUCCESS;
                    mCallFunction.onCall(jsEntity, mMethodUrl);
                }*/


            }
        });

        ibtn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GoHomeEvent homeEvent = new GoHomeEvent();
//                homeEvent.view = v;
//                EventBus.getDefault().post(homeEvent);
            }
        });
    }


    public void setEvaluate(String json) {
        this.mJson = json;
        tv_right.setText("评价规则");
        tv_right.setVisibility(View.VISIBLE);
        ibtn_more.setVisibility(View.GONE);
    }


    public void setAction(String data) {
//        if (!TextUtils.isEmpty(mBridgeWebView.getUrl()) && mBridgeWebView.getUrl().contains(UrlUtils.getInstance().getMp())) {
            data = "{\"type\":\"mp\"}";
//        }
        try {
            JSONObject json = new JSONObject(data);
            if (json.has("type")) {
                type = json.optString("type").trim();
                if (type.contains(",")) {
                    String[] array = type.split("\\,");
                    type = array[0];
                }
                if (!TextUtils.isDigitsOnly(type)) {
                    type = "";
                } else if (type.equals("20") || type.equals("21") || type.equals("22")) {//filer water,electric,gas
//                    type = "";
                    tv_right.setText("缴费记录");
                }
                tv_right.setVisibility(!TextUtils.isEmpty(type) ? View.VISIBLE : View.GONE);
                ibtn_more.setVisibility(View.GONE);
            } else {
                ibtn_more.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
        }
    }


   /* @Override
    public void setInitData(TitleBean titleBean, IJSCallFunction callFunction, String url) {
        super.setInitData(titleBean, callFunction, url);

        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(titleBean.getWidgets().get(0).getText());

    }
*/
   @Override
    public void setTitle(String title) {
        onSetTitle(title);
    }
}
