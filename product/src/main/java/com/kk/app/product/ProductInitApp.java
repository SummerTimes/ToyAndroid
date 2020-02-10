package com.kk.app.product;

import android.app.Application;
import android.util.Log;
import com.kk.app.lib.widget.component.IComponentInit;

/**
 * @author kk
 * @datetime 2020-01-14
 * @desc
 */
public class ProductInitApp implements IComponentInit {

    @Override
    public void onInit(Application app) {
        Log.e("xp", "----init-----ProductInitApp--");
        Log.e("xp", "----init-----ProductInitApp--:" + app.getPackageName());
    }
}
