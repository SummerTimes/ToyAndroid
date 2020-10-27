package com.bl.flutter.channel

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.billy.cc.core.component.CC
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject

class FlutterJumpChannel : ChannelCall {
    override fun onCall(call: MethodCall, result: MethodChannel.Result, context: Context): Boolean {
        if (TextUtils.equals(call.method, "jumpPage")) {
            (call.arguments as? String)?.let {
                try {
                    jump(it, context)
                } catch (e: Exception) {
                    result.error("","参数错误","")
                }
                result.success("")
            }
            return true
        } else if (TextUtils.equals(call.method, "jumpPageWithCallBack")) {
            (call.arguments as? String)?.let {
                try {
                    jumpWithCallBack(it, context, result)
                } catch (e: Exception) {
                    Toast.makeText(context,e.message.toString(),Toast.LENGTH_LONG).show()
                }
            }
            return true
        }

        return false
    }

    private fun jumpWithCallBack(it: String, context: Context, channelResult: MethodChannel.Result) {
        val jsonObject = JSONObject(it)
        val cn = jsonObject.optString("companentName")
        val action = jsonObject.optString("actionName")
        val param = jsonObject.optString("data")
        if (cn.isNotEmpty() && action.isNotEmpty()) {
            val js = if (param.isNotEmpty()) {
                JSONObject(param)
            } else {
                JSONObject()
            }
            CC.obtainBuilder(cn)
                    .setActionName(action)
//                    .setParams(js)
                    .setContext(context)
                    .build()
                    .callAsyncCallbackOnMainThread { cc, result ->
                        val re = JSONObject()
                        if (result.isSuccess) {
                            re.put("result", "success")
                            re.put("data", result.data)
                            channelResult.success(re.toString())
                        } else {
                            re.put("result", "error")
                            channelResult.success(re.toString())
                        }
                    }
        }
    }

    private fun jump(it: String, context: Context) {
        val jsonObject = JSONObject(it)
        val cn = jsonObject.optString("companentName")
        val action = jsonObject.optString("actionName")
        val param = jsonObject.optString("data")
        if (cn.isNotEmpty() && action.isNotEmpty()) {
            var js = if (param.isNotEmpty()) {
                JSONObject(param)
            } else {
                JSONObject()
            }
            CC.obtainBuilder(cn)
                    .setActionName(action)
//                    .setParams(js)
                    .setContext(context)
                    .build()
                    .callAsync()
        }
    }
}