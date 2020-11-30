package com.kk.app.mine.util

import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.kk.app.lib.widget.BLToast
import com.kk.app.mine.constant.MineConstant

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
object MineUtils {
    /**
     * 跳转MineActivity
     *
     * @param cc 参数
     */
    fun openMineActivity(cc: CC) {
        val param = cc.getParamItem<String>(MineConstant.Companion.KRY_PARAM)
        if (TextUtils.isEmpty(param)) {
            BLToast.showToast(cc.context, "参数不能为空")
            return
        }
        val context = cc.context
//        val intent = Intent(context, MineActivity::class.java)
//        val intent = Intent(context, DemoListActivity::class.java)
//        if (context !is Activity) {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        }
//        intent.putExtra(MineConstant.KRY_PARAM, param)
//        context.startActivity(intent)
    }
}