package com.kk.app.lib.rv.title;

import com.kk.app.lib.rv.R;

import android.view.ViewGroup;
import com.kk.app.lib.rv.BaseFloorHolderFactory;
import com.kk.app.lib.rv.FloorViewType;
import com.kk.app.lib.rv.BaseFloorHolder;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc 纯文字的title
 */
public class FloorTitleTextHolerFactory extends BaseFloorHolderFactory {
    @Override
    public BaseFloorHolder createFloorHolder(ViewGroup parent) {
        return new FloorTitleTextHolder(getItemView(parent));
    }

    @Override
    public int getLayoutId() {
        return R.layout.floor_title_text;
    }

    @Override
    public int getViewType() {
        return FloorViewType.VIEW_TYPE_TITLE_TEXT;
    }
}
