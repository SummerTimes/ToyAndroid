package com.kk.app.lib.rv.kln

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
abstract class KotlinFloor<T> {

    var data: T? = null

    abstract fun viewType(): Int

    private lateinit var holder: KotlinHolder

    fun onBindHolder(holder: KotlinHolder) {
        onMyBind(holder)
    }

    abstract fun onMyBind(holder: KotlinHolder)

}