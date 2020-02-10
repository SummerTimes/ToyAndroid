package com.kk.app.lib.widget.scrollspeed;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
public class ScrollSpeedHelper {
    private static int durationSwitch = 500;

    /**
     * @param viewpager ViewPager控件
     */
    public static void controlViewPagerSpeed(ViewPager viewpager) {
        if (viewpager != null) {
            controlViewPagerSpeed(viewpager.getContext(), viewpager, durationSwitch);
        }
    }

    /**
     * @param viewpager ViewPager控件
     * @param duration  滑动延时
     */
    public static void controlViewPagerSpeed(ViewPager viewpager, int duration) {
        if (viewpager != null) {
            if (duration > 0) {
                controlViewPagerSpeed(viewpager.getContext(), viewpager, duration);
            } else {
                controlViewPagerSpeed(viewpager.getContext(), viewpager, durationSwitch);
            }
        }
    }


    /**
     * 设置ViewPager的滑动时间
     *
     * @param context        上下文
     * @param viewpager      ViewPager控件
     * @param durationSwitch 滑动延时
     */
    private static void controlViewPagerSpeed(Context context, ViewPager viewpager, int durationSwitch) {
        try {
            Field mField;

            mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            ScrollSpeed mScroller = new ScrollSpeed(context, new AccelerateInterpolator());
            mScroller.setDurationTime(durationSwitch);
            mField.set(viewpager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
