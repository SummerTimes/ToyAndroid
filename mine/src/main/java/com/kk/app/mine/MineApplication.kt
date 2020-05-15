package com.kk.app.mine

import android.app.Application
import com.kk.app.lib.network.NetworkConfig
import com.squareup.picasso.Picasso

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
class MineApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkConfig.setup(this, "prd")
    }
}