package com.kk.app.mobile.utils;

import android.content.Context;
import android.content.Intent;

import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.kk.app.mobile.R;
import com.kk.app.mobile.activity.AppNetWorkActivity;

public class EnvSwitchKit implements IKit {

    private static final String TAG = "NetWorkType";

    @Override
    public int getCategory() {
        return Category.BIZ;
    }
 
    @Override
    public int getName() {
        return R.string.app_ambient;
    }
 
    @Override
    public int getIcon() {
        return R.drawable.app_net_work;
    }
 
    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, AppNetWorkActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
 
    @Override
    public void onAppInit(Context context) {
    
    }
}