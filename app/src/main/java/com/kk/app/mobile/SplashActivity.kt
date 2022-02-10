package com.kk.app.mobile

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.kk.app.mobile.utils.AppJumpUtil

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activity)
        initMain()
    }

    private fun initMain() {
        Handler().postDelayed({
            AppJumpUtil.startMainAvy(this@SplashActivity)
            finish()
        }, 1000)
    }
}