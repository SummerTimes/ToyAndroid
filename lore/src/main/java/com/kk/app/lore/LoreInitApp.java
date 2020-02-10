package com.kk.app.lore;

import android.app.Application;
import android.util.Log;
import com.kk.app.lib.widget.component.IComponentInit;

/**
 * @author kk
 * @datetime 2020-01-14
 * @desc
 */
public class LoreInitApp implements IComponentInit {

    @Override
    public void onInit(Application app) {
        Log.e("xp", "----init-----LoreInitApp--");
        Log.e("xp", "----init-----LoreInitApp--:" + app.getPackageName());
    }
}
