package com.kk.app.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.ref.WeakReference;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:自定义Toast
 */
@SuppressLint("InflateParams")
public class BLToast {

    private static Toast toast;
    private static TextView tv;
    private static boolean canceled = true;
    private static Handler mHandler = null;
    private static Runnable mRunnable = null;

    /***
     * @param context
     * @param msg     吐司内容
     */
    public static void showToast(final Context context, final String msg) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }
        Context applicationContext=context.getApplicationContext();
        if (Looper.myLooper() != Looper.getMainLooper()) {//当前线程不是主线程
            //放在主线程运行
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showToast(context, msg);
                }
            });
            return;
        }
        if (toast == null) {
            toast = new Toast(applicationContext);
            View toastRoot = LayoutInflater.from(applicationContext).inflate(
                    R.layout.lw_custom_toast_layout, null);
            // toastRoot.setAlpha((float) 0.7);
            toast.setView(toastRoot);
            tv = (TextView) toastRoot.findViewById(R.id.custom_toast_text);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        tv.setText(msg);
        toast.show();
    }


//    public static void showToast(Context context, String msg, final int time) {
//        Toast toast = showToast(context, msg);
//        showMyToast(toast, time);
//    }
//
//
//    /**
//     * 控制时间
//     */
//    public static void showMyToast(final Toast toast, final int cnt) {
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.show();
//            }
//        }, 0, Toast.LENGTH_LONG);
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                toast.cancel();
//                timer.cancel();
//            }
//        }, cnt);
//
//    }

    public static void showToast(WeakReference<Context> context, String msg,
                                 Float time) {
        if (!canceled) {
            return;
        }
        View toastRoot = LayoutInflater.from(context.get()).inflate(
                R.layout.lw_custom_toast_layout, null);
        if (toast == null) {
            toast = new Toast(context.get());
            // toastRoot.setAlpha((float) 0.7);
            toast.setView(toastRoot);
            tv = (TextView) toastRoot.findViewById(R.id.custom_toast_text);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 200);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        tv.setText(msg);
        // toast.show();
        show(time);
    }

    private static void show(final Float time) {
        if (mHandler == null) {
            mHandler = new Handler();
            mRunnable = new Runnable() {
                public void run() {
                    showUntilCancel();
                }
            };
        }
        mHandler.removeCallbacks(mRunnable);
        canceled = false;
        showUntilCancel();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    mHandler.removeCallbacks(mRunnable);
                    canceled = true;
                    toast.cancel();
                    toast = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, (long) (time * 1000));
    }

    private static void showUntilCancel() {
        if (canceled)
            return;
        toast.show();
        mHandler.postDelayed(mRunnable, 3500);
    }
}
