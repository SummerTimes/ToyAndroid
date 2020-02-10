package com.kk.app.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author uis on 2018/3/12.
 * @author uis modify 2018/5/22 使用静态内部类处理，避免持有外部的this指针
 */

public class TimerUtils {
    private static Handler mHandler = new TimerHandler();
    private Timer mTimer;

    public static TimerUtils createTimer() {
        return new TimerUtils();
    }

    private TimerUtils() {
    }

    public void startLoopTimer(long mills, Runnable run) {
        this.startTimer(true, true, mills, run);
    }

    public void startTimer(boolean isMainLoop, boolean isLooper, long mills, Runnable run) {
        if(this.mTimer == null) {
            this.mTimer = new Timer();
        }
        try {
            if (isLooper) {
                this.mTimer.schedule(this.getTimerTask(isMainLoop, run), mills, mills);
            } else {
                this.mTimer.schedule(this.getTimerTask(isMainLoop, run), mills);
            }
        }catch (Exception ex){}
    }

    public void stopTimer() {
        if(this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
    }

    private TimerTask getTimerTask(boolean isMainLoop, Runnable run) {
        return new TimerTaskInner(isMainLoop,run);
    }

    static class TimerHandler extends Handler{
        public TimerHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            try{
                if(msg.what == 1000 && msg.obj!=null && msg.obj instanceof Runnable){
                    ((Runnable)(msg.obj)).run();
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    static class TimerTaskInner extends TimerTask{
        boolean isMainLooper;
        Runnable run;

        public TimerTaskInner(boolean isMainLooper,Runnable run) {
            this.isMainLooper = isMainLooper;
            this.run = run;
        }

        @Override
        public void run() {
            try {
                if (run != null) {
                    if (isMainLooper) {
                        mHandler.obtainMessage(1000,run).sendToTarget();
                    } else {
                        run.run();
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
