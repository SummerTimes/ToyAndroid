package com.bl.flutter.channel

import android.content.Context
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

interface ChannelCall {
    fun onCall(call: MethodCall, result: MethodChannel.Result, context: Context):Boolean
}