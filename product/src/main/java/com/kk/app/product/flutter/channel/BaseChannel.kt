package com.bl.flutter.channel

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.kk.app.lib.network.NetworkConfig
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject


class BaseChannel : ChannelCall {
    override fun onCall(call: MethodCall, result: MethodChannel.Result, context: Context): Boolean {
        when (call.method) {
            "back" -> run {
                (context as? Activity)?.onBackPressed()
                result.success("success")

                return true
            }
            "getNetworkConfigInfo" -> run {
                val infoObj = JSONObject()
                try {
                    val pi = context.packageManager.getPackageInfo(context.packageName, 0)
                    infoObj.put("appVersion", pi.versionName)
                    val baseUrl = if ("sit" == NetworkConfig.getNetworkEnvType()) "https://mh5.st.bl.com/h5_gateway" else "https://mh5.bl.com/h5_gateway"
                    infoObj.put("baseUrl", baseUrl)

                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
                result.success(infoObj.toString())

                return true
            }
            "getH5ConfigInfo" -> run {
                val infoObj = JSONObject()
                try {
                    val h5Url = if ("sit" == NetworkConfig.getNetworkEnvType()) "https://mh5.st.bl.com/" else "https://mh5.bl.com/"
                    infoObj.put("h5Url", h5Url)
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
                result.success(infoObj.toString())

                return true
            }
            "getNativeUserInfo" -> run {
                var userInfo = ""
                try {
                    val ccResult = CC.obtainBuilder("LoginComponent")
                            .setActionName("actionUserInfo")
                            .build()
                            .call()
                    if (ccResult.isSuccess) {
                        val data = ccResult.data
                        userInfo = data.optString("userInfo")
                    }
                } catch (e: PackageManager.NameNotFoundException) {
                    e.printStackTrace()
                }
                result.success(userInfo)

                return true
            }
            "copyMes" -> {
                try {
                    val js = JSONObject(call.arguments.toString())
//                    if (!TextUtils.isEmpty(call.arguments.toString())) {
//                        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
//                        // 将文本内容放到系统剪贴板里。
//                        cm.primaryClip = ClipData.newPlainText(null, js.optString("copyMes"))
//                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                result.success("success")
                return true
            }
            "sensor_click" -> {
                val js = JSONObject(call.arguments.toString())
//                SensorsDataAnalysis.trackClickBtn(js.optString("buttonPage"), js.optString("buttonName"), js.optString("categoryId"), js.optString("buttonId"), js)
                result.success("success")

                return true
            }
            "sensor_page_view" -> {
                val js = JSONObject(call.arguments.toString())
//                SensorsDataAnalysis.trackPageView(js.optString("pageId"), js.optString("categoryId"))
                result.success("success")

                return true
            }
            "toast" -> {
//                Tips.show(context, call.arguments.toString())
                result.success("success")
                return true
            }
        }
        return false
    }
}