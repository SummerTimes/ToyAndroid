package com.kk.app.lore.presenter

import com.kk.app.lib.rv.manager.*
import java.util.*

/**
 * @Auther: yd
 * @datetime: 2018/10/24
 * @desc:
 */

class TestPresenter(key: Int) : AbstractPresenter(key) {


    override fun visitNet(isPullDown: Boolean) {

    }

    override fun provideCombinesProvider(): ICombinesProvider {
        return Provider()
    }


    fun getFloorManager(): FloorManager {
        return floorManager
    }
}


class Provider : ICombinesProvider {
    override fun createCombines(key: Int): MutableList<IFloorCombine> {
        val arrayList = ArrayList<IFloorCombine>()
        arrayList.add(TestCombine(key))
        arrayList.add(TestCombine(key))
        arrayList.add(TestCombine(key))
        return arrayList
    }
}
