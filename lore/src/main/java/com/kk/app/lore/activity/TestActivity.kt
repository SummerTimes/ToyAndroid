package com.kk.app.lore.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kk.app.lib.rv.manager.IFloorCombine
import com.kk.app.lib.rv.manager.IUI
import com.kk.app.lore.R
import com.kk.app.lore.presenter.TestPresenter

/**
 * @Auther: yd
 * @datetime: 2018/10/24
 * @desc:
 */
class TestActivity : Activity(), IUI {
    
    private lateinit var testPresenter: TestPresenter

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testPresenter = TestPresenter(1)
        setContentView(R.layout.lore_test_activity)
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.adapter = testPresenter.adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        testPresenter.onUIReady(this)
    }

    override fun onCombineRequestInflateUI(combine: IFloorCombine?) {
        testPresenter.notifyDataChanged(combine)
    }
}