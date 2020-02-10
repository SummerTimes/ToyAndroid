package com.billy.android.preloader.util;

import android.text.TextUtils;

import com.billy.android.preloader.MultiEveryInfoDataListener;
import com.billy.android.preloader.MultipleGroupDataListener;
import com.billy.android.preloader.PreLoader;
import com.billy.android.preloader.interfaces.GroupedDataListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：杨松
 * 日期：2018/5/21 16:16
 */

public class WrapperHelper {
    Map<String, Object> map;

    public boolean listenData(int id, final MultipleGroupDataListener listener, final String[] keys) {
        if (map == null) {
            map = new HashMap<>(keys.length);
        }
        int count = keys.length;
        for (final String key : keys) {
            if (TextUtils.isEmpty(key)) {
                count--;
                continue;
            }
            if (count < 0) {
                count = 0;
            }
            final int finalCount = count;
            PreLoader.listenData(id, new GroupedDataListener<Object>() {
                @Override
                public String keyInGroup() {
                    return key;
                }

                @Override
                public void onDataArrived(Object o) {
                    map.put(key, o);
                    if (map.size() == finalCount) {
                        listener.onDataArrived(map);
                    }
                }
            });
        }
        return false;
    }


    public boolean listenData(int id, final MultiEveryInfoDataListener listener, final String[] keys) {
        if (map == null) {
            map = new HashMap<>(keys.length);
        }
        for (final String key : keys) {
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            PreLoader.listenData(id, new GroupedDataListener<Object>() {
                @Override
                public String keyInGroup() {
                    return key;
                }

                @Override
                public void onDataArrived(Object o) {
                    map.put(key, o);
                    listener.onDataArrived(map, key);
                }
            });
        }
        return false;
    }


}
