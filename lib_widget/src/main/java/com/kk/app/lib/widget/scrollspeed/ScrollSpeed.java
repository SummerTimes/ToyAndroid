package com.kk.app.lib.widget.scrollspeed;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
class ScrollSpeed extends Scroller {

    private int durationTime = 1500; // 默认滑动速度 1500ms

    public ScrollSpeed(Context context) {
        super(context);
    }

    public ScrollSpeed(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, durationTime);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, durationTime);
    }

    public void setDurationTime(int time) {
        if (time > 0) {
            durationTime = time;
        }
    }

    public int getDurationTime() {
        return durationTime;
    }
}
