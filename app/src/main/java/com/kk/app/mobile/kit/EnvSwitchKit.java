package com.kk.app.mobile.kit;

import android.content.Context;

import com.didichuxing.doraemonkit.kit.Category;
import com.didichuxing.doraemonkit.kit.IKit;
import com.kk.app.lib.widget.BLToast;
import com.kk.app.mobile.R;

public class EnvSwitchKit implements IKit {
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
        return R.mipmap.icon_home_v3;
    }
 
    @Override
    public void onClick(Context context) {
        BLToast.showToast(context,"你没");
//        DebugService service = ServiceManager.getInstance().getService(context, DebugService.class);
//        PageManager.getInstance().startFragment(service.getContainer(), EnvSwitchFragment.class);
    }
 
    @Override
    public void onAppInit(Context context) {
    
    }
}