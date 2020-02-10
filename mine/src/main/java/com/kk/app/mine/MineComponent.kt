package com.kk.app.mine

import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.kk.app.mine.constant.MineConstant
import com.kk.app.mine.fragment.MineFragment
import com.kk.app.mine.util.MineUtils

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc: Mine/Component
 */
class MineComponent : IComponent {
    override fun getName(): String {
        return MineConstant.Companion.KRY_MINE_COMPONENT
    }

    override fun onCall(cc: CC): Boolean {
        val actionName = cc.actionName
        // 我的/Activity
        if (TextUtils.equals(MineConstant.Companion.KRY_MINE_ACTIVITY, actionName)) {
            MineUtils.openMineActivity(cc)
            CC.sendCCResult(cc.callId, CCResult.success())
        } else if (TextUtils.equals(MineConstant.Companion.KRY_MINE_FRAGMENT, actionName)) {
            CC.sendCCResult(cc.callId, CCResult.success("fragment", MineFragment.Companion.newInstance("我的")))
            return true
        } else {
            CC.sendCCResult(cc.callId, CCResult.error("actionName not specified"))
        }
        return false
    }
}