package com.kk.app.mine.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.kk.app.mine.R
import com.kk.app.mine.constant.MineConstant

/**
 * @author kk
 * @datetime: 2019/08/07
 * @desc: 我的
 */
class MineFragment : Fragment() {
    private var rootView: View? = null
    private var mParam: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam = arguments!!.getString(MineConstant.Companion.KRY_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mine_fragment, container, false)
        } else {
            val viewGroup = rootView!!.parent as ViewGroup
            viewGroup?.removeView(rootView)
        }
        initView()
        return rootView
    }

    private fun initView() {
        rootView!!.findViewById<View>(R.id.tv_info).setOnClickListener {
            CC.obtainBuilder("mineComponent")
                    .setActionName("mineActivity")
                    .addParam("param", "我的12345")
                    .build()
                    .callAsync()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(param: String?): MineFragment {
            val fragment = MineFragment()
            val args = Bundle()
            args.putString(MineConstant.Companion.KRY_PARAM, param)
            fragment.arguments = args
            return fragment
        }
    }
}