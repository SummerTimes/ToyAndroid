package com.kk.app.mobile

import android.app.Application
import android.text.TextUtils
import android.util.Log
import com.kk.app.lib.network.NetworkConfig
import com.kk.app.lib.widget.component.ComponentManager
import com.kk.app.lib.widget.component.IComponentInit
import com.kk.app.mobile.utils.AppDeviceUtil


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
}