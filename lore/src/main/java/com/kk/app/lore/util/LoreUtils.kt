package com.kk.app.lore.util

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.kk.app.lore.activity.RecommendActivity
import com.kk.app.lore.constant.LoreConstant

/**
 * @author kk
 * @datetime 2019-08-07
 * @desc
 */
object LoreUtils {

    /**
     * 跳转Lore/Activity
     *
     * @param cc 参数
     */
    fun openLoreActivity(cc: CC) {
        val param = cc.getParamItem<String>(LoreConstant.KRY_PARAM)
        if (TextUtils.isEmpty(param)) {
            return
        }
        val context = cc.context
        val intent = Intent(context, RecommendActivity::class.java)
        if (context !is Activity) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        intent.putExtra(LoreConstant.KRY_PARAM, param)
        context.startActivity(intent)
    }

}
