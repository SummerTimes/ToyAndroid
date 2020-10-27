package com.bl.flutter.channel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.billy.cc.core.component.CC
import com.google.gson.JsonObject
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject

class TargetMethodChannel : ChannelCall {
    override fun onCall(call: MethodCall, result: MethodChannel.Result, context: Context): Boolean {

        if (TextUtils.equals(call.method, "loadAsyncCallResultMethod")) {
            Log.d("targetMethod", "==>${call.arguments}")
            (call.arguments as? String)?.let {
                kotlin.runCatching {
                    switchTarget(it, result,context)
                }
            }
            return true
        }
        return false
    }

    private fun switchTarget(arguments: String, result: MethodChannel.Result, context: Context) {
        val jsonObject = JSONObject(arguments)
        val target = jsonObject.optString("targetName")
        val method = jsonObject.optString("method")
        when (target) {
            "BaseConfig" -> handelBaseConfig(method, result, jsonObject,context)
            "sensor" -> handleSensor(method, result, jsonObject,context)
            else-> component(target,method,result,jsonObject,context)
        }
    }

    private fun component(target: String?, method: String?, result: MethodChannel.Result, jsonObject: JSONObject, context: Context) {
//        CC.obtainBuilder(target).setActionName(method).setContext(context)
//                .setParams(jsonObject).build()
//                .callAsyncCallbackOnMainThread { cc, r ->
//                    if(r.data !=null ) {
//                        result.success(r.data.toString())
//                    }else{
//                        result.success(null)
//                    }
//                }
    }

    private fun handleSensor(method: String?, result: MethodChannel.Result, jsonObject: JSONObject, context: Context) {
        when (method) {
            "submitOrder" -> {
//                SensorsDataAnalysis.trackSubmitOrder(jsonObject.optString("buttonPage"),
//                        jsonObject.optString("productList"), null)
                result.success("")
            }
            "wholeInfo"->{
//                result.success(SensorsAnalysisUtil.getInstance().wholeStationInfo)
            }
        }
    }

    private fun handelBaseConfig(method: String, result: MethodChannel.Result, jsonObject: JSONObject, context: Context) {
        val obj = JsonObject()
//        obj.addProperty("deviceNum",NetworkConfig.deviceNum)
//        obj.addProperty("deviceId",SensorsDataAPI.sharedInstance().presetProperties.optString("\$device_id", NetworkConfig.deviceNum))
//        val dataItem = CC.obtainBuilder("BLChannelComponent").setActionName("getApkChannel").build().call().getDataItem<String>("channel")
//        obj.addProperty("channel", dataItem?:"官方渠道")
        try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            obj.addProperty("version",pi.versionName)
        } catch (e: Exception) {
        }
        result.success(obj.toString())
    }


}

