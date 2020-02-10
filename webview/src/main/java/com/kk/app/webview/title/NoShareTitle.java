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
public class NoShareTitle extends BaseTitle {


    private View titleBtnLeft;
    private View shareInTitle;
    private View titleBtnRight;
    private TextView titleContent;

    public NoShareTitle(Activity activity) {
        super(activity);
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);

        titleBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(bridgeWebView);
            }
        });
        titleBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                GoHomeEvent homeEvent = new GoHomeEvent();
//                homeEvent.view = view;
//                EventBus.getDefault().post(homeEvent);
            }
        });

    }

    @Override
    protected void onSetTitle(String data) {
        titleContent.setText(data);
    }


    @Override
    public void initOnCreateTitle() {

        titleBtnLeft = findView(R.id.title_btn_left);
        titleBtnRight = findView(R.id.title_btn_right);

        titleContent = (TextView) findView(R.id.title_content);
        titleBtnRight.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayout() {
        return R.layout.bl_web_no_share_title;
    }
}
