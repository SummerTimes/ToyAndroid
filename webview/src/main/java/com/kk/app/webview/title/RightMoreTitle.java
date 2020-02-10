package com.kk.app.webview.title;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kk.app.web.bljsbridge.BridgeWebView;
import com.klcw.app.web.R;

import java.util.logging.Logger;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class RightMoreTitle extends BaseTitle implements View.OnClickListener {

    private ImageView mBackImage;
    private TextView mTitleText;
    private ImageView mMoreImage;

    private PopupWindow mPopupWindow;

    private int messageCount;

    public RightMoreTitle(Activity activity) {
        super(activity);
    }

    @Override
    protected void initOnCreateTitle() {
        mBackImage = (ImageView) findView(R.id.iv_back);
        mTitleText = (TextView) findView(R.id.tv_txt);
        mMoreImage = (ImageView) findView(R.id.more_image);
    }

    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);
        mBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(bridgeWebView);
            }
        });

        mMoreImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showMorePop();
            }
        });

//        RequestUtils.getInstance().getMessageUnread();
    }

    @Override
    protected int getLayout() {
        return R.layout.bl_web_title_appointment;
    }

    @Override
    protected void onSetTitle(String data) {
        mTitleText.setText(data);
    }


    @SuppressLint("InflateParams")
    private void showMorePop() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.bl_pup_good_more, null);

        popupView.findViewById(R.id.msg_layout).setOnClickListener(this);
        popupView.findViewById(R.id.home_layout).setOnClickListener(this);
        popupView.findViewById(R.id.search_layout).setOnClickListener(this);
        popupView.findViewById(R.id.concern_layout).setOnClickListener(this);
        popupView.findViewById(R.id.history_layout).setOnClickListener(this);

        final ImageView more_msg = popupView.findViewById(R.id.more_msg);
//        messageCount = RequestUtils.getInstance().getMessageCount();
//        if (messageCount > 0) {
//            more_msg.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_msg2_v3));
//        } else {
//            more_msg.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.icon_msg_v3));
//        }
        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }

        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
//        mPopupWindow.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.bg_visiable));
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.showAsDropDown(mMoreImage, 20, 50);

    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.msg_layout) {
           /* if (LoginUtils.isLogin(getActivity())) {
                GoodsClicked.pageId(getActivity(), "messageCenterHome", null);
            } else {
                GoodsClicked.toast(getActivity(), getActivity().getResources().getString(R.string.nologin_msg));
//                LoginUtils.login(getActivity(), mKey);

                LoginUtils.login(getActivity(), new LoginActionFragment.LoginCallBack() {
                    @Override
                    public void onLoginBack(int result) {
                        if (result == LOGIN_SUCESS) {
                            RequestUtils.getInstance().getMessageUnread();
                        }
                    }
                });
            }
            dismissPop();
            return;
        }
        if (v.getId() == R.id.home_layout) {
            CC.obtainBuilder(ConstCommon.BL_MAIN_COMPONENT)
                    .setContext(getActivity())
                    .setActionName("go_home")
                    .build()
                    .callAsync();
            dismissPop();
            return;
        }
        if (v.getId() == R.id.search_layout) {
            GoodsClicked.searchView(getActivity(), "");
            dismissPop();
            return;
        }
        if (v.getId() == R.id.concern_layout) {
            if (LoginUtils.isLogin(getActivity())) {
                GoodsClicked.pageId(getActivity(), "mystoreaddrcollection", null);
                dismissPop();
                return;
            }

            JSONObject data = new JSONObject();
            try {
                data.put("paramNeedLogin", true);//强制需要登录状态
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CC.obtainBuilder("LoginComponent")
                    .setActionName("actionLoginStatus")
                    .setParams(data)
                    .setContext(getActivity())
                    .build()
                    .callAsyncCallbackOnMainThread(new IComponentCallback() {
                        @Override
                        public void onResult(CC cc, CCResult ccResult) {
                            if (ccResult.isSuccess()) {
                                //获取成功处理
                                JSONObject data = ccResult.getData();
                                if (data.optBoolean("isLogin")) {
                                    //已登录状态
                                    GoodsClicked.pageId(getActivity(), "mystoreaddrcollection", null);
                                    dismissPop();
                                } else {
                                    //未登录状态
                                }
                            } else {
                                //获取失败处理
                            }
                        }
                    });
            return;*/
//        }
    }


    private void dismissPop() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }
}
