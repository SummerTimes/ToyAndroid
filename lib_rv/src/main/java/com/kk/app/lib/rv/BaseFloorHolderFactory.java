package com.kk.app.lib.rv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc 楼层ViewHolder工厂
 */
public abstract class BaseFloorHolderFactory implements IFloorHolderFactory {

    protected View getItemView(ViewGroup parent) {
        try {
            int layoutId = getLayoutId();
            if (layoutId != 0) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                return inflater.inflate(layoutId, parent, false);
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
