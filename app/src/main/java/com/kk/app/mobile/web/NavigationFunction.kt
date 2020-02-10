package com.kk.app.mobile.web

import android.app.Activity
import android.content.Context
import com.kk.app.web.bljsbridge.AbstractFunction
import com.kk.app.web.bljsbridge.BridgeWebView

/**
 * @author kk
 * @datetime 2019-08-06
 * @desc
 */
class NavigationFunction : AbstractFunction() {

    override fun registerFunction(webView: BridgeWebView, context: Context) {
        super.registerFunction(webView, context)
        openSelfStoreMapPage(webView, context as Activity)
    }

    /**
     * @param webView
     * @param context
     */
    private fun openSelfStoreMapPage(webView: BridgeWebView, context: Activity) {
        /*webView.registerFunction(NAVIGATION_TRANS) { method, data, url, ijsCallFunction ->
            val info = "玩我呢？"
            try {
                val entity = JSEntity()
                val jsonObject = JSONObject()
                jsonObject.put("key", info)
                Log.e("xp", "-----jsonObject.toString()-------$jsonObject")
                //  entity.data = TextUtils.isEmpty(info) ? "{}" : info;
                entity.data = jsonObject.toString()
                entity.status = BridgeConfig.SUCCESS
                ijsCallFunction.onCall(entity, url)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }*/
    }

    companion object {
        private const val NAVIGATION_TRANS = "Demo" + "#" + "push"
    }
}