package com.kk.app.lib.rv.kln

import com.kk.app.lib.rv.R
import kotlinx.android.synthetic.main.floor_title_text.*

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
class TestFloor : KotlinFloor<String>() {

    override fun onMyBind(holder: KotlinHolder) {
        holder.tv_name. text = data
    }

    override fun viewType(): Int {
        return R.layout.floor_title_text
    }

}