package com.kk.app.mobile

import android.app.Application
import android.content.Context
import android.text.TextUtils
import com.bytedance.boost_multidex.BoostMultiDex
import com.kk.app.lib.network.NetworkConfig
import com.kk.app.lib.widget.component.ComponentManager
import com.kk.app.lib.widget.component.IComponentInit
import com.kk.app.mobile.utils.AppDeviceUtil
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.view.FlutterMain


/**
 * @author kk
 * @datetime 2019-08-06
 * @desc 程序入口
 */
class MemberApplication : Application() {

    private val TAG = "xp"

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        BoostMultiDex.install(base)
    }


    override fun onCreate() {
        super.onCreate()
        FlutterMain.startInitialization(this)
        NetworkConfig.setup(this, getNetWorkType())
        initComponent(this)
        setFlutter()
    }

    private fun setFlutter() {
        val flutterEngine = FlutterEngine(this)
        // Start executing Dart code to pre-warm the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        // Cache the FlutterEngine to be used by FlutterActivity.
        FlutterEngineCache
                .getInstance()
                .put("bl_flutter_engine", flutterEngine)
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
            if (TextUtils.isEmpty(type)) {
                AppDeviceUtil.setNetWorkType(this, BuildConfig.networkEnvType)
            } else {
                envType = type
            }
        }
        return envType
    }
}