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
public class RedShareTitle extends BaseTitle {

    private View shareInTitle;
    private TextView title_content;
    private View title_btn_right;
    private View btn_title_left;

    public RedShareTitle(Activity activity) {
        super(activity);
    }


    @Override
    public void registerFunction(final BridgeWebView bridgeWebView) {
        super.registerFunction(bridgeWebView);

        btn_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack(bridgeWebView);
            }
        });
        shareInTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bridgeWebView.callJs("window.appShare");
            }
        });


        title_btn_right.setOnClickListener(new View.OnClickListener() {
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
        title_content.setText(data);
    }


    @Override
    public void initOnCreateTitle() {
        title_btn_right = findView(R.id.title_btn_right);
        btn_title_left = findView(R.id.title_btn_left);
        shareInTitle = findView(R.id.share_in_title);
        title_btn_right.setVisibility(View.VISIBLE);
        shareInTitle.setVisibility(View.VISIBLE);
        title_content = (TextView) findView(R.id.title_content);
    }

    @Override
    protected int getLayout() {
        return R.layout.bl_web_common_title;
    }
}
