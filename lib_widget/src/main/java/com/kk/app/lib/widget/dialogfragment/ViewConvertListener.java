package com.kk.app.lib.widget.dialogfragment;

import java.io.Serializable;

/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
public interface ViewConvertListener extends Serializable {
    long serialVersionUID = System.currentTimeMillis();

    void convertView(ViewHolder holder, BaseNiceDialog dialog);
}
