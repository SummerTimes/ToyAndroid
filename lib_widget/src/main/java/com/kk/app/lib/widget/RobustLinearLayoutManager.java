package com.kk.app.lib.widget;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:暂时解决recycleView崩溃问题
 */
public class RobustLinearLayoutManager extends LinearLayoutManager {

    public RobustLinearLayoutManager(Context context) {
        super(context);
    }

    public RobustLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception ignored) {

        }
    }
}