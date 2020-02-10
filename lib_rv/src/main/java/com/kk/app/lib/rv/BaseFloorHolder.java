package com.kk.app.lib.rv;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.annotations.Nullable;

/**
 * @author billy.qi
 * @since 18/1/18 18:33
 */
public abstract class BaseFloorHolder<T> extends RecyclerView.ViewHolder {

    public BaseFloorHolder(View itemView) {
        super(itemView);
    }

    public @Nullable
    String TAG;

    /**
     * 绑定数据
     *
     * @param data 楼层信息
     */
    public abstract void bind(T data);
}
