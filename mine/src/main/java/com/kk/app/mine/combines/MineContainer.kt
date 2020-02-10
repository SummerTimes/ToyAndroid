package com.kk.app.mine.combines

import com.kk.app.lib.rv.manager.ICombinesProvider
import com.kk.app.lib.rv.manager.IFloorCombine
import com.kk.app.lib.rv.manager.TestCombine
import java.util.*

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
class MineContainer : ICombinesProvider {
    override fun createCombines(key: Int): List<IFloorCombine> {
        val iFloorCombines: MutableList<IFloorCombine> = ArrayList()
        iFloorCombines.add(TestCombine(key))
        return iFloorCombines
    }
}