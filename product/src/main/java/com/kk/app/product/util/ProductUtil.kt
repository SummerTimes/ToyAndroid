package com.kk.app.product.util

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.kk.app.lib.widget.BLToast
import com.kk.app.product.activity.PdTestAvy
import com.kk.app.product.activity.ProductMainActivity
import com.kk.app.product.constant.ProductConstant

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
object ProductUtil {
    /**
     * 跳转Product/Activity
     *
     * @param cc 参数
     */
    fun openProductActivity(cc: CC) {
        val param = cc.getParamItem<String>(ProductConstant.Companion.KRY_PARAM)
        if (TextUtils.isEmpty(param)) {
            BLToast.showToast(cc.context, "参数不能为空")
            return
        }
        val context = cc.context
        val intent = Intent(context, ProductMainActivity::class.java)
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.putExtra(ProductConstant.Companion.KRY_PARAM, param)
        context.startActivity(intent)
    }

    /**
     * 跳转
     *
     * @param cc 参数
     */
    fun openTestAvy(cc: CC) {
        val param = cc.getParamItem<String>(ProductConstant.KRY_PARAM)
        if (TextUtils.isEmpty(param)) {
            return
        }
        val context = cc.context
        val intent = Intent(context, PdTestAvy::class.java)
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.putExtra(ProductConstant.KRY_PARAM, param)
        context.startActivity(intent)
    }
}