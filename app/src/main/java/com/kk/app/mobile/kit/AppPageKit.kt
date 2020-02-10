package com.kk.app.mobile.kit

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.billy.cc.core.component.CC
import com.kk.app.lib.widget.utils.LxStatusBarUtil
import com.kk.app.mobile.R
import org.json.JSONObject
import java.lang.ref.WeakReference

/**
 * @author kk
 * @datetime: 2018/10/29
 * @desc:底部Kit
 */
class AppPageKit(activity: FragmentActivity) : View.OnClickListener {

    private val mLlLore: LinearLayout
    private val mImLore: ImageView
    private val mTvLore: TextView
    private val mLlProduct: LinearLayout
    private val mImProduct: ImageView
    private val mTvProduct: TextView
    private val mLlMine: LinearLayout
    private val mImMine: ImageView
    private val mTvMine: TextView
    private var currentTag: String? = null
    private val mTextViews: Array<TextView>?
    private val mImageViews: Array<ImageView>?
    private var mActivity: WeakReference<FragmentActivity>?
    
    /**
     * Activity onResume
     */
    fun onResume() {}

    /**
     * 页面切换 Fragment
     *
     * @param tag
     */
    private fun switchPage(tag: String) {
        if (TextUtils.equals(tag, currentTag)) {
            return
        }
        updateTextByTag(tag)
        val fragment = getTagFragment(tag)
        fragment?.let { showFragment(it) } ?: createTagFragment(tag)
        hideTagElseFragment(tag)
    }

    /**
     * 根据Tag 更新文字的颜色值/图片
     *
     * @param tag
     */
    private fun updateTextByTag(tag: String) {
        currentTag = tag
        val tagIndex = getTagIndex(tag)
        if (tagIndex < 0 || null == mTextViews || null == mImageViews) {
            return
        }
        run {
            var i = 0
            val size = mTextViews!!.size
            while (i < size) {
                if (tagIndex == i) {
                    setTextViewColor(mTextViews[i], true)
                    mTextViews[tagIndex].isSelected = true
                } else {
                    setTextViewColor(mTextViews[i], false)
                    if (mTextViews[i].isSelected) {
                        mTextViews[i].isSelected = false
                    }
                }
                i++
            }
        }
        var i = 0
        val size = mImageViews.size
        while (i < size) {
            if (tagIndex == i) {
                setImageView(mImageViews[i], true)
                mImageViews[tagIndex].isSelected = true
            } else {
                setImageView(mImageViews[i], false)
                if (mImageViews[i].isSelected) {
                    mImageViews[i].isSelected = false
                }
            }
            i++
        }
    }

    /**
     * 设置颜色值
     *
     * @param textView
     * @param tag
     */
    private fun setTextViewColor(textView: TextView, tag: Boolean) {
        if (tag) {
            textView.setTextColor(ContextCompat.getColor(mActivity!!.get()!!, R.color.app_000000))
        } else {
            textView.setTextColor(ContextCompat.getColor(mActivity!!.get()!!, R.color.app_999999))
        }
    }

    /**
     * 设置图片
     *
     * @param imageView
     * @param tag
     */
    private fun setImageView(imageView: ImageView, tag: Boolean) { // 博客
        if (imageView.id == R.id.im_lore) {
            setImageViewColor(imageView, tag, R.mipmap.app_lore, R.mipmap.app_select_lore)
        } else if (imageView.id == R.id.im_product) {
            setImageViewColor(imageView, tag, R.mipmap.app_product, R.mipmap.app_select_product)
        } else if (imageView.id == R.id.im_mine) {
            setImageViewColor(imageView, tag, R.mipmap.app_mine, R.mipmap.app_select_mine)
        }
    }

    /**
     * 设置/图片
     *
     * @param imageView
     * @param tag
     * @param unSelectId
     * @param selectId
     */
    private fun setImageViewColor(imageView: ImageView, tag: Boolean, @DrawableRes unSelectId: Int, @DrawableRes selectId: Int) {
        if (tag) {
            imageView.setImageDrawable(ContextCompat.getDrawable(mActivity!!.get()!!, selectId))
        } else {
            imageView.setImageDrawable(ContextCompat.getDrawable(mActivity!!.get()!!, unSelectId))
        }
    }

    /**
     * on Intent
     *
     * @param params
     */
    fun onIntentAction(params: String) {
        if (!TextUtils.isEmpty(params) && "null" != params) {
            try {
                val jsonObject = JSONObject(params)
                val action = jsonObject.optString("action")
                //过滤非法action
                if (getTagIndex(action) >= 0) {
                    switchPage(action)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        var index = -1
        if (R.id.ll_lore == id) {
            index = 0
        } else if (R.id.ll_product == id) {
            index = 1
        } else if (R.id.ll_mine == id) {
            index = 2
        }
        if (index >= 0) {
            switchPage(sPages[index])
        }
    }

    /**
     * 根据Tag获取 int类型下标
     *
     * @param tag
     * @return
     */
    private fun getTagIndex(tag: String): Int {
        var index = -1
        var i = 0
        val size = sPages.size
        while (i < size) {
            if (TextUtils.equals(sPages[i], tag)) {
                index = i
                break
            }
            i++
        }
        return index
    }

    /**
     * 创建主页fragment
     *
     * @param tag
     * @return
     */
    private fun createTagFragment(tag: String): Fragment? {
        var fragment: Fragment? = null
        if (TextUtils.equals(tag, sPages[0])) {
            fragment = getComponentFragment("loreComponent", "loreFragment", "fragment")
        } else if (TextUtils.equals(tag, sPages[1])) {
            fragment = getComponentFragment("productComponent", "productFragment", "fragment")
        } else if (TextUtils.equals(tag, sPages[2])) {
            fragment = getComponentFragment("mineComponent", "mineFragment", "fragment")
        }
        fragment?.let { addTagFragment(it, tag) }
        return fragment
    }

    /**
     * 获取对应的fragment
     *
     * @param componentName 组件名 componentName
     * @param actionName    组件 actionName
     * @param key           组件 KEY
     * @return
     */
    private fun getComponentFragment(componentName: String, actionName: String, key: String): Fragment? {
        var fragment: Fragment? = null
        val ccResult = CC.obtainBuilder(componentName)
                .setActionName(actionName)
                .build()
                .call()
        if (ccResult != null && ccResult.isSuccess) {
            try {
                fragment = ccResult.getDataItem<Fragment>(key)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return fragment
    }

    /**
     * 根据tag 添加Fragment
     *
     * @param fragment
     * @param tag
     */
    private fun addTagFragment(fragment: Fragment?, tag: String) {
        if (fragment != null) {
            mActivity!!.get()!!.supportFragmentManager.beginTransaction().add(R.id.content, fragment, tag).commitAllowingStateLoss()
        }
    }

    /**
     * 根据Tag 获取Fragment
     *
     * @param tag
     * @return
     */
    private fun getTagFragment(tag: String): Fragment? {
        return mActivity!!.get()!!.supportFragmentManager.findFragmentByTag(tag)
    }

    /**
     * 显示Fragment
     *
     * @param fragment
     */
    private fun showFragment(fragment: Fragment?) {
        if (fragment != null) {
            mActivity!!.get()!!.supportFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss()
        }
    }

    /**
     * 隐藏非tag的fragment
     *
     * @param tag
     */
    private fun hideTagElseFragment(tag: String) {
        for (item in sPages) {
            if (!TextUtils.equals(item, tag)) {
                hideFragment(getTagFragment(item))
            }
        }
    }

    /**
     * 隐藏Fragment
     *
     * @param fragment
     */
    private fun hideFragment(fragment: Fragment?) {
        if (fragment != null) {
            mActivity!!.get()!!.supportFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss()
        }
    }

    /**
     * 清除全部Fragment
     */
    fun release() {
        for (tag in sPages) {
            removeTagFragment(tag)
        }
        mActivity = null
    }

    /**
     * 移除所有的fragment
     *
     * @param tag
     */
    private fun removeTagFragment(tag: String) {
        val fragment = getTagFragment(tag)
        if (fragment != null) {
            mActivity!!.get()!!.supportFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss()
        }
    }

    /**
     * 设置Status bar
     */
    private fun setStatusBar() {
        LxStatusBarUtil.setLightMode(mActivity!!.get())
    }

    companion object {
        val sPages = arrayOf("lore", "product", "mine")
    }

    /**
     * 初始化View
     *
     */
    init {
        mActivity = WeakReference(activity)
        mLlLore = activity.findViewById(R.id.ll_lore)
        mImLore = activity.findViewById(R.id.im_lore)
        mTvLore = activity.findViewById(R.id.tv_lore)
        mLlLore.setOnClickListener(this)
        mLlProduct = activity.findViewById(R.id.ll_product)
        mImProduct = activity.findViewById(R.id.im_product)
        mTvProduct = activity.findViewById(R.id.tv_product)
        mLlProduct.setOnClickListener(this)
        mLlMine = activity.findViewById(R.id.ll_mine)
        mImMine = activity.findViewById(R.id.im_mine)
        mTvMine = activity.findViewById(R.id.tv_mine)
        mLlMine.setOnClickListener(this)
        mTextViews = arrayOf(mTvLore, mTvProduct, mTvMine)
        mImageViews = arrayOf(mImLore, mImProduct, mImMine)
        switchPage(sPages[0])
        setStatusBar()
    }
}