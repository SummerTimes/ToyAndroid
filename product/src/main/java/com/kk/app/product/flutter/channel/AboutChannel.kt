package com.bl.flutter.channel

import android.content.Context
import android.text.TextUtils
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject
import java.io.InputStreamReader
import java.util.*

class AboutChannel : ChannelCall {
    override fun onCall(call: MethodCall, result: MethodChannel.Result, context: Context): Boolean {
        if (TextUtils.equals(call.method, "getAboutInfo")) {
            val properties = Properties()

            try {
                val inputStream = context.assets.open("about_i_bai_lian.properties")
                val isr = InputStreamReader(inputStream, "UTF-8")   //指定格式
                properties.load(isr)
                val js = JSONObject()
                js.put("time", properties.getProperty("publish_time"))
                val pi = context.packageManager.getPackageInfo(context.packageName, 0)
                js.put("version", pi.versionName)
                js.put("content", properties.getProperty("content"))
                js.put("experience", properties.getProperty("experience_optimization"))
                js.put("newFuction", properties.getProperty("add_new_function"))
                result.success(js.toString())
            } catch (e: Exception) {
                e.printStackTrace()
//                result.error("fail", )
            }
            return true
        }
        return false
    }
}