package com.kk.app.login;

import android.app.Application;
import android.util.Log;
import com.kk.app.lib.widget.component.IComponentInit;

/**
 * @author kk
 * @datetime 2020-01-14
 * @desc
 */
public class LoginInitApp implements IComponentInit {

    @Override
    public void onInit(Application app) {
        Log.e("xp", "----init-----LoginInitApp--");
        Log.e("xp", "----init-----LoginInitApp--:" + app.getPackageName());
    }
}
