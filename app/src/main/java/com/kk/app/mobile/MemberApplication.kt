package com.kk.app.mobile

import android.app.Application
import android.content.Context
import android.os.Build
import android.text.TextUtils
import com.bytedance.boost_multidex.BoostMultiDex
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoost.BoostLifecycleListener
import com.idlefish.flutterboost.Utils
import com.idlefish.flutterboost.interfaces.INativeRouter
import com.kk.app.lib.network.NetworkConfig
import com.kk.app.lib.widget.component.ComponentManager
import com.kk.app.lib.widget.component.IComponentInit
import com.kk.app.mobile.flutter.PageRouter
import com.kk.app.mobile.flutter.TextPlatformViewFactory
import com.kk.app.mobile.utils.AppDeviceUtil
import io.flutter.embedding.android.FlutterView
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.EventChannel.EventSink
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec
import okhttp3.internal.platform.Platform


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
        initRouter();
        NetworkConfig.setup(this, getNetWorkType())
        initComponent(this)

    }

    private fun initRouter() {
        val router = INativeRouter { context, url, urlParams, requestCode, exts ->
            val assembleUrl = Utils.assembleUrl(url, urlParams)
            PageRouter.openPageByUrl(context, assembleUrl, urlParams)
        }

        val boostLifecycleListener: BoostLifecycleListener = object : BoostLifecycleListener {
            override fun beforeCreateEngine() {}
            override fun onEngineCreated() {

                // 注册MethodChannel，监听flutter侧的getPlatformVersion调用
                val methodChannel = MethodChannel(FlutterBoost.instance().engineProvider().dartExecutor, "flutter_native_channel")
                methodChannel.setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method == "getPlatformVersion") {
                        result.success(Build.VERSION.RELEASE)
                    } else {
                        result.notImplemented()
                    }
                }
                val methodChannel2 = EventChannel(FlutterBoost.instance().engineProvider().dartExecutor, "native_flutter_channel")
                methodChannel2.setStreamHandler(object : EventChannel.StreamHandler {
                    override fun onListen(arguments: Any, events: EventSink) {
                        events.success("msg")
                    }

                    override fun onCancel(arguments: Any) {}
                })

                // 注册PlatformView viewTypeId要和flutter中的viewType对应
                FlutterBoost
                        .instance()
                        .engineProvider()
                        .platformViewsController
                        .registry
                        .registerViewFactory("plugins.test/view", TextPlatformViewFactory(StandardMessageCodec.INSTANCE))
            }

            override fun onPluginsRegistered() {}
            override fun onEngineDestroy() {}
        }

        //
        // AndroidManifest.xml 中必须要添加 flutterEmbedding 版本设置
        //
        //   <meta-data android:name="flutterEmbedding"
        //               android:value="2">
        //    </meta-data>
        // GeneratedPluginRegistrant 会自动生成 新的插件方式　
        //
        // 插件注册方式请使用
        // FlutterBoost.instance().engineProvider().getPlugins().add(new FlutterPlugin());
        // GeneratedPluginRegistrant.registerWith()，是在engine 创建后马上执行，放射形式调用
        //


        //
        // AndroidManifest.xml 中必须要添加 flutterEmbedding 版本设置
        //
        //   <meta-data android:name="flutterEmbedding"
        //               android:value="2">
        //    </meta-data>
        // GeneratedPluginRegistrant 会自动生成 新的插件方式　
        //
        // 插件注册方式请使用
        // FlutterBoost.instance().engineProvider().getPlugins().add(new FlutterPlugin());
        // GeneratedPluginRegistrant.registerWith()，是在engine 创建后马上执行，放射形式调用
        //
        val platform: com.idlefish.flutterboost.Platform? = FlutterBoost.ConfigBuilder(this, router)
                .isDebug(true)
                .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                .renderMode(FlutterView.RenderMode.texture)
                .lifecycleListener(boostLifecycleListener)
                .build()
        FlutterBoost.instance().init(platform)


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