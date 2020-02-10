package com.kk.app.lib.rv;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc 楼层工厂接口
 */
public interface IFloorHolderFactory {

    /**
     * 创建楼层ViewHolder
     *
     * @param parent 父容器
     * @return 给RecyclerView用的ViewHolder
     */
    @NonNull
    BaseFloorHolder createFloorHolder(ViewGroup parent);

    @LayoutRes
    int getLayoutId();

    /**
     * view的类型，必须唯一
     *
     * @return
     */
    int getViewType();
}
