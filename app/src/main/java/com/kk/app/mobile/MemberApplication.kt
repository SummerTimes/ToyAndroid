package com.kk.app.mobile

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.kit.IKit
import com.kk.app.lib.network.NetworkConfig
import com.kk.app.lib.widget.component.ComponentManager
import com.kk.app.lib.widget.component.IComponentInit
import com.kk.app.mobile.utils.AppDeviceUtil
import com.kk.app.mobile.utils.EnvSwitchKit
import java.util.*


/**
 * @author kk
 * @datetime 2019-08-06
 * @desc 程序入口
 */
class MemberApplication : Application() {

    private val TAG = "xp"

    override fun onCreate() {
        super.onCreate()
        NetworkConfig.setup(this, getNetWorkType())
        initComponent(this);
        initAmbientKit();
    }

    /**
     * 初始化组件Application
     */
    private fun initComponent(memberApplication: MemberApplication) {
        val component: List<IComponentInit> = ComponentManager.getApplication()
        for (componentInit in component) {
            componentInit.onInit(memberApplication)
        }
    }

    private fun getNetWorkType(): String? {
        var envType: String? = BuildConfig.networkEnvType
        if (BuildConfig.DEBUG) {
            val type: String = AppDeviceUtil.getNetWorkType(this)
            Log.e(TAG, "------type----"+type);
            if (TextUtils.isEmpty(type)) {
                AppDeviceUtil.setNetWorkType(this, BuildConfig.networkEnvType)
            } else {
                envType = type
            }
        }
        return envType
    }

    /**
     * 初始化滴滴环境工具
     */
    private fun initAmbientKit() {
        val kits: MutableList<IKit> = ArrayList()
        kits.add(EnvSwitchKit())
        DoraemonKit.install(this, kits)
    }
}