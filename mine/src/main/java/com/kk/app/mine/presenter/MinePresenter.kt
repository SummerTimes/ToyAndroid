package com.kk.app.mine.presenter

import com.billy.android.preloader.PreLoader
import com.kk.app.lib.rv.manager.AbstractPresenter
import com.kk.app.lib.rv.manager.ICombinesProvider
import com.kk.app.mine.combines.MineContainer
import com.kk.app.mine.dataload.MinDataLoad

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
class MinePresenter(key: Int) : AbstractPresenter(key) {
    override fun visitNet(isPullDown: Boolean) {
        PreLoader.refresh(mKey)
    }

    override fun provideCombinesProvider(): ICombinesProvider {
        return MineContainer()
    }

    companion object {
        /**
         * 加载数据
         *
         * @param par
         * @return
         */
        fun preLoad(par: String?): Int {
            return PreLoader.preLoad<String>(MinDataLoad(par))
        }
    }
}