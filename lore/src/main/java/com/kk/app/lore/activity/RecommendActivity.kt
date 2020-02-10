package com.kk.app.lore.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.billy.cc.core.component.CC
import com.kk.app.lore.R

/**
 * 推荐模块
 */
class RecommendActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lore_main_activity)
        initView()
    }

    private fun initView() {
        findViewById<Button>(R.id.btn_kt).setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            CC.obtainBuilder("loginComponent")
                    .setContext(this)
                    .setActionName("LoginActivity")
                    .addParam("param", "登陆/模块")
                    .build()
                    .callAsync()
        }
    }

}
