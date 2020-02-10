package com.kk.app.lib.rv.manager

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
abstract class DynamicProvider : ICombinesProvider {

    override fun createCombines(key: Int): MutableList<IFloorCombine> {
        return emptyArray<IFloorCombine>().toMutableList()
    }

    private lateinit var presenter: AbstractPresenter
    private var key: Int = 0
    private lateinit var floorManager: FloorManager

    fun onCreate(floorManager: FloorManager, presenter: AbstractPresenter, key: Int) {
        this.floorManager = floorManager
        this.presenter = presenter
        this.key = key
        onProviderCreate(floorManager,presenter,key)
    }

    abstract fun onProviderCreate(floorManager: FloorManager, presenter: AbstractPresenter, key: Int)

    fun add(list: MutableList<IFloorCombine>) {
        floorManager.clearCombines()
        floorManager.addAll(list, key, presenter)
    }
}