package com.kk.app.mobile

import android.app.Application
import android.util.Log
import androidx.multidex.MultiDex
import com.kk.app.lib.network.NetworkConfig
import com.kk.app.lib.widget.component.ComponentManager
import com.kk.app.lib.widget.component.IComponentInit

/**
 * @author kk
 * @datetime 2019-08-06
 * @desc 程序入口
 */
class MemberApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        NetworkConfig.setup(this, "prd")
        initComponent(this);
    }

    /**
     * 初始化组件Application
     */
    private fun initComponent(memberApplication: MemberApplication) {
        Log.e("xp", "------initComponent----");
        val component: List<IComponentInit> = ComponentManager.getApplication()
        for (componentInit in component) {
            componentInit.onInit(memberApplication)
        }
    }

}