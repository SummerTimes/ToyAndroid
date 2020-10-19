package com.kk.app.product.holder;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kk.app.product.R;
import com.kk.app.product.rv.ItemViewHolder;


/**
 * @author yangdong
 */
public class PtFanItem extends PtCommonItem {

    private CharSequence mTitle;


    public PtFanItem(@NonNull CharSequence title) {
        super(R.layout.pt_common_item);
        mTitle = title;
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        super.bind(holder, position);
        final TextView titleTv = holder.findViewById(R.id.commonItemTitleTv);
        titleTv.setText(mTitle);

//        ImageView commonItemIv = holder.findViewById(R.id.commonItemIv);

    }
}
