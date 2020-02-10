package com.kk.app.product

import android.app.Application
import com.kk.app.lib.network.NetworkConfig

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
class ProductApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkConfig.setup(this, "prd")
    }
}