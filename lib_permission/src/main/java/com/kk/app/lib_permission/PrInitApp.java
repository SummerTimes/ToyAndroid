package com.kk.app.lib_permission;

import android.app.Application;
import android.util.Log;

import com.kk.app.lib.widget.component.IComponentInit;
import com.kk.app.lib_permission.utils.PrUtils;

/**
 * @author kk
 * @datetime 2020-01-14
 * @desc 权限初始化
 */
public class PrInitApp implements IComponentInit {

    @Override
    public void onInit(Application app) {
        Log.e("xp", "----init-----PrisnInitApp--");
        Log.e("xp", "----init-----PrisnInitApp--:" + app.getPackageName());
        PrUtils.init(app);
    }
}
