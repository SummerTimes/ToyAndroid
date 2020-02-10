package com.kk.app.mobile

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.ViewStub
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kk.app.mobile.constant.AppMethod
import com.kk.app.mobile.kit.AppPageKit

/**
 * @author kk
 * @datetime 2018/10/24
 * @desc 程序入口
 */
class MainActivity : AppCompatActivity() {
    private var mIsExit = false
    private var mAppPageKit: AppPageKit? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_main_activity)
        initView()
    }

    private fun initView() {
        (findViewById<View>(R.id.vs_mainPage) as ViewStub).inflate()
        mAppPageKit = AppPageKit(this)
    }

    override fun onResume() {
        super.onResume()
        mAppPageKit!!.onResume()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (getIntent().extras != null) {
            if (null != mAppPageKit) {
                getIntent().extras?.getString(AppMethod.KRY_PARAM)?.let { mAppPageKit!!.onIntentAction(it) }
            }
        }
    }

    override fun onDestroy() {
        if (null != mAppPageKit) {
            mAppPageKit!!.release()
        }
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                finish()
            } else {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show()
                mIsExit = true
                Handler().postDelayed({ mIsExit = false }, 2000)
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 桌面快捷启动1、发微博；2、热门微博；3、扫一扫
     */
    private fun setupShortcuts() {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val mShortcutManager = getSystemService(ShortcutManager::class.java)
            val infos: MutableList<ShortcutInfo> = ArrayList()
            var intent: Intent
            var info: ShortcutInfo
            val mainIntent = Intent(Intent.ACTION_MAIN, Uri.EMPTY,
                    this, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            // 发微博
            intent = Intent(this, RecommendActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.putExtra("isPublishWeibo", true)
            intent.putExtra("from_shortcuts", true)
            info = ShortcutInfo.Builder(this, resources.getString(R.string.shortcuts_publish_weibo_long_disable_msg))
                    .setShortLabel(resources.getString(R.string.shortcuts_publish_weibo_short_name))
                    .setLongLabel(resources.getString(R.string.shortcuts_publish_weibo_long_name))
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntents(arrayOf(mainIntent, intent)).build()
            infos.add(info)
            // 热门微博
            intent = Intent(this, MineActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            intent.putExtra("isPublishWeibo", false)
            intent.putExtra("from_shortcuts", true)
            info = ShortcutInfo.Builder(this, resources.getString(R.string.shortcuts_popular_weibo_disable_msg))
                    .setShortLabel(resources.getString(R.string.shortcuts_popular_weibo_short_name))
                    .setLongLabel(resources.getString(R.string.shortcuts_popular_weibo_long_name))
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntents(arrayOf(mainIntent, intent)).build()
            infos.add(info)
            // 扫一扫
            intent = Intent(this, ProductMainActivity::class.java)
            intent.action = Intent.ACTION_VIEW
            info = ShortcutInfo.Builder(this, resources.getString(R.string.shortcuts_qrcord_disable_msg))
                    .setShortLabel(resources.getString(R.string.shortcuts_qrcord_short_name))
                    .setLongLabel(resources.getString(R.string.shortcuts_qrcord_long_name))
                    .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher))
                    .setIntent(intent).setIntents(arrayOf(mainIntent, intent)).build()
            infos.add(info)
            mShortcutManager.dynamicShortcuts = infos
        }*/
    }
}