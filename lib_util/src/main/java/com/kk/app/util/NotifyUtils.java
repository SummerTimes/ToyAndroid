package com.kk.app.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.ArrayMap;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Map;

/**
 * @author uis on 2018/5/16.
 */
public class NotifyUtils {
    private static int  ID = 0x01ff;
    private NotificationManager manager;
    private Map<Integer, NotificationCompat.Builder> map;


    public NotifyUtils(Context context) {
        map = new ArrayMap<>();
        manager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);
    }

    public static boolean isNotifyEnabled(Context context){
        boolean enable = true;
        try {
            NotificationManagerCompat manager = NotificationManagerCompat.from(context.getApplicationContext());
            enable = manager.areNotificationsEnabled();
        }catch (Exception e){}
        return enable;
    }

    public int showProgressBar(Context mContext, int iconResId,PendingIntent intent){
        int notificationId = ID++;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setDefaults(Notification.DEFAULT_LIGHTS)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setShowWhen(false)
                .setSmallIcon(iconResId)
                .setContentIntent(intent)
                .setContentTitle("下载中... 0%")
                .setProgress(100,0,false);
        try {
            builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), iconResId));
        }catch (Exception ex){}
        Notification notify = builder.build();
        notify.flags |= Notification.PRIORITY_MAX;
        manager.notify(notificationId, notify);
        map.put(notificationId,builder);
        return notificationId;
    }

    public void cancel(int id) {
        map.remove(id);
        manager.cancel(id);
    }

    public void cancelAll() {
        map.clear();
        manager.cancelAll();
    }

    public void updateProgress(int id,int progress) {
        try{
            NotificationCompat.Builder builder = map.get(id);
            if (null != builder) {
                builder.setProgress(100, progress, false);
                builder.setContentTitle("下载中... "+progress+"%");
                manager.notify(id, builder.build());
            }
        }catch (Exception ex){

        }
    }
}
