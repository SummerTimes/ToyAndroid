package com.kk.app.mine.dataload

import com.billy.android.preloader.interfaces.GroupedDataLoader

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
class MinDataLoad(par: String?) : GroupedDataLoader<String> {
    override fun keyInGroup(): String {
        return MINE_LIST_KEY
    }

    override fun loadData(): String {
        return "你好"
    }

    companion object {
        const val MINE_LIST_KEY = "MinDataLoadKey"
    }
}