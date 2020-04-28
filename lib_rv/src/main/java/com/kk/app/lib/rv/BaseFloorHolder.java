package com.kk.app.lib.rv;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @author billy.qi
 * @since 18/1/18 18:33
 */
public abstract class BaseFloorHolder<T> extends RecyclerView.ViewHolder {

    public BaseFloorHolder(View itemView) {
        super(itemView);
    }

    String TAG;

    /**
     * 绑定数据
     *
     * @param data 楼层信息
     */
    public abstract void bind(T data);
}
