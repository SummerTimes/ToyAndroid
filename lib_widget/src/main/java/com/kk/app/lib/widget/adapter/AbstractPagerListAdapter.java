package com.kk.app.lib.widget.adapter;

import android.view.View;

import java.util.List;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
public abstract class AbstractPagerListAdapter<T> extends AbstractPagerAdapter {
    private final List<T> mData;

    public AbstractPagerListAdapter(List<T> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public abstract View newView(int position);

    protected T getItem(int position) {
        return mData.get(position);
    }
}
