package com.kk.app.product.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.billy.cc.core.component.CC
import com.kk.app.product.R
import com.kk.app.product.constant.ProductConstant

/**
 * @author kk
 * @datetime: 2019/08/07
 * @desc: 我的
 */
class ProductFragment : Fragment() {
    private var rootView: View? = null
    private var mParam: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam = arguments!!.getString(ProductConstant.Companion.KRY_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.pt_main_fragment, container, false)
        } else {
            val viewGroup = rootView!!.parent as ViewGroup
            viewGroup?.removeView(rootView)
        }
        initView()
        return rootView
    }

    private fun initView() {
        rootView!!.findViewById<View>(R.id.tv_info).setOnClickListener {
            CC.obtainBuilder("productComponent")
                    .setActionName("productActivity")
                    .addParam("param", "product/12345")
                    .build()
                    .callAsync()
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {
        fun newInstance(param: String?): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putString(ProductConstant.Companion.KRY_PARAM, param)
            fragment.arguments = args
            return fragment
        }
    }
}