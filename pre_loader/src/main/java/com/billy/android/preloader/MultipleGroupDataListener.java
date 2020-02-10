package com.billy.android.preloader;

import com.billy.android.preloader.interfaces.DataListener;

import java.util.Map;

/**
 * 作者：杨松
 * 日期：2018/5/21 16:09
 */

public abstract class MultipleGroupDataListener implements DataListener<Map<String, Object>> {
    @Override
    public void onDataArrived(Map<String, Object> stringObjectMap) {

    }
}
