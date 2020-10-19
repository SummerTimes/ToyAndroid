package com.kk.app.product.holder;

import android.app.PendingIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.kk.app.product.R;
import com.kk.app.product.rv.ItemViewHolder;
import com.kk.app.product.util.NotificationUtils;
import com.kk.app.product.util.Utils;


/**
 * @author yangdong
 */
public class PtNullItem extends PtCommonItem {

    public PtNullItem() {
        super(R.layout.pt_null_item);
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        super.bind(holder, position);
        ImageView im_null = holder.findViewById(R.id.im_null);
        TextView tv_title = holder.findViewById(R.id.tv_title);
        tv_title.setText(""+NotificationUtils.areNotificationsEnabled());


        im_null.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NotificationUtils.notify(0, new Utils.Consumer<NotificationCompat.Builder>() {
                    @Override
                    public void accept(NotificationCompat.Builder builder) {

                        builder.setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("title")
                                .setContentText("content text: 内容123456789")
                                .setAutoCancel(true);

                    }
                });
            }
        });



    }
}
