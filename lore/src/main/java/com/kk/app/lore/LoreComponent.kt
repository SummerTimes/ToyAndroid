package com.kk.app.lore

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.kk.app.lore.constant.LoreConstant
import com.kk.app.lore.fragment.LoreFragment
import com.kk.app.lore.util.LoreUtils

/**
 * @author kk
 * @datetime 2019-08-07
 * @desc
 */
class LoreComponent : IComponent {

    override fun getName(): String = LoreConstant.KRY_LORE_COMPONENT

    override fun onCall(cc: CC): Boolean {
        when (cc.actionName) {
            LoreConstant.KRY_LORE_ACTIVITY -> {
                LoreUtils.openLoreActivity(cc)
                CC.sendCCResult(cc.callId, CCResult.success())
            }
            LoreConstant.KRY_LORE_FRAGMENT -> {
                CC.sendCCResult(cc.callId, CCResult.success("fragment", LoreFragment.newInstance("Lore")))
                return true
            }
            else -> {
                CC.sendCCResult(cc.callId, CCResult.error("actionName not specified"))
            }
        }
        return false
    }
}
