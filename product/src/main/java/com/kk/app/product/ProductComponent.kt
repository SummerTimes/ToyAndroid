package com.kk.app.product

import android.text.TextUtils
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.kk.app.product.constant.ProductConstant
import com.kk.app.product.fragment.ProductFragment
import com.kk.app.product.util.ProductUtil

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc: Mine/Component
 */
class ProductComponent : IComponent {
    override fun getName(): String {
        return ProductConstant.Companion.KRY_PRODUCT_COMPONENT
    }

    override fun onCall(cc: CC): Boolean {
        val actionName = cc.actionName
        // 体系/Activity
        if (TextUtils.equals(ProductConstant.KRY_PRODUCT_ACTIVITY, actionName)) {
            ProductUtil.openProductActivity(cc)
            CC.sendCCResult(cc.callId, CCResult.success())
        }else if(TextUtils.equals(ProductConstant.KRY_TEST_ACTIVITY, actionName)) {
            ProductUtil.openTestAvy(cc)
            CC.sendCCResult(cc.callId, CCResult.success())
        } else if (TextUtils.equals(ProductConstant.KRY_PRODUCT_FRAGMENT, actionName)) {
            CC.sendCCResult(cc.callId, CCResult.success("fragment", ProductFragment.Companion.newInstance("体系")))
//            CC.sendCCResult(cc.callId, CCResult.success("fragment", BlankFragment.newInstance("体系")))
            return true
        } else {
            CC.sendCCResult(cc.callId, CCResult.error("actionName not specified"))
        }
        return false
    }
}