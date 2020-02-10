package com.kk.app.lib.rv.title;

import android.view.View;
import android.widget.TextView;

import com.kk.app.lib.rv.Floor;
import com.kk.app.lib.rv.BaseFloorHolder;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc 纯文字的title标题的ViewHolder
 */
public class FloorTitleTextHolder extends BaseFloorHolder<Floor<TitleTextUnit>> {

    private TextView editText;

    public FloorTitleTextHolder(View itemView) {
        super(itemView);
        this.editText = (TextView) itemView;
    }

    @Override
    public void bind(Floor<TitleTextUnit> data) {
        if (data != null && data.getData() != null) {
            editText.setText(data.getData().title);
        }
    }
}
