package com.kk.app.mine;

import android.app.Application;
import android.util.Log;
import com.kk.app.lib.widget.component.IComponentInit;

/**
 * @author kk
 * @datetime 2020-01-14
 * @desc
 */
public class MineInitApp implements IComponentInit {

    @Override
    public void onInit(Application app) {
        Log.e("xp", "----init-----MineInitApp--");
        Log.e("xp", "----init-----MineInitApp--:" + app.getPackageName());
    }
}
