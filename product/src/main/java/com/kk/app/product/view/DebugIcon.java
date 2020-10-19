package com.kk.app.product.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.kk.app.product.R;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/08/26
 *     desc  :
 * </pre>
 */
public class DebugIcon extends RelativeLayout {


    public DebugIcon(Context context) {
        super(context);
        inflate(getContext(), R.layout.du_debug_icon, this);
        TouchUtils.setOnTouchListener(this, new TouchUtils.OnTouchUtilsListener() {

            private int rootViewWidth;
            private int rootViewHeight;
            private int viewWidth;
            private int viewHeight;
            private int statusBarHeight;

            @Override
            public boolean onDown(View view, int x, int y, MotionEvent event) {
                viewWidth = view.getWidth();
                viewHeight = view.getHeight();
                View contentView = view.getRootView().findViewById(android.R.id.content);
                rootViewWidth = contentView.getWidth();
                rootViewHeight = contentView.getHeight();
                statusBarHeight = getStatusBarHeight();

                processScale(view, true);
                return true;
            }

            @Override
            public boolean onMove(View view, int direction, int x, int y, int dx, int dy, int totalX, int totalY, MotionEvent event) {
                view.setX(Math.min(Math.max(0, view.getX() + dx), rootViewWidth - viewWidth));
                view.setY(Math.min(Math.max(statusBarHeight, view.getY() + dy), rootViewHeight - viewHeight));
                return true;
            }

            @Override
            public boolean onStop(View view, int direction, int x, int y, int totalX, int totalY, int vx, int vy, MotionEvent event) {
                stick2HorizontalSide(view);
                processScale(view, false);
                return true;
            }

            private void stick2HorizontalSide(View view) {
                view.animate()
                        .setInterpolator(new DecelerateInterpolator())
                        .translationX(view.getX() + viewWidth / 2f > rootViewWidth / 2f ? rootViewWidth - viewWidth : 0)
                        .setDuration(100)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
//                                savePosition();
                            }
                        })
                        .start();
            }

            private void processScale(final View view, boolean isDown) {
                float value = isDown ? 1 - 0.1f : 1;
                view.animate()
                        .scaleX(value)
                        .scaleY(value)
                        .setDuration(100)
                        .start();
            }
        });
    }

    /**
     * Return the status bar's height.
     *
     * @return the status bar's height
     */
    public static int getStatusBarHeight() {
        Resources resources = Resources.getSystem();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}
