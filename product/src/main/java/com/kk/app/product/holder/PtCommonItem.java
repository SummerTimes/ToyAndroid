package com.kk.app.product.holder;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import com.kk.app.product.rv.BaseItem;
import com.kk.app.product.rv.ItemViewHolder;

;

/**
 * @author kk
 */
public class PtCommonItem<T extends BaseItem> extends BaseItem<T> {


    private int backgroundColor;

    public PtCommonItem(int layoutId) {
        super(layoutId);
    }

    @CallSuper
    @Override
    public void bind(@NonNull final ItemViewHolder holder, int position) {
//        holder.itemView.setBackgroundColor(backgroundColor);
    }

    public PtCommonItem<T> setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }
}
