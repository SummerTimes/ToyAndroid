package com.kk.app.lib.widget;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:点击事件
 */
public abstract class NoDoubleClickListener implements OnClickListener {
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

    protected abstract void onNoDoubleClick(View v);
}
