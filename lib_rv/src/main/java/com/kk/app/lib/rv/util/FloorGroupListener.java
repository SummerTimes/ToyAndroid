package com.kk.app.lib.rv.util;

import androidx.annotation.NonNull;

import com.billy.android.preloader.interfaces.GroupedDataListener;
import com.kk.app.lib.rv.manager.AbstractFloorCombine;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class FloorGroupListener<Data> implements GroupedDataListener<Data> {

    private final AbstractFloorCombine combine;
    private final GroupedDataListener<Data> groupedDataListener;

    public FloorGroupListener(@NonNull AbstractFloorCombine combine, GroupedDataListener<Data> groupedDataListener) {
        this.combine = combine;
        this.groupedDataListener = groupedDataListener;
    }

    @Override
    public void onDataArrived(Data data) {
        if (isFloorEmpty(data)) {
            combine.getFloors().clear();
            combine.infoDataSetChanged();
        }
        if (this.groupedDataListener == null) {
            return;
        }
        this.groupedDataListener.onDataArrived(data);
    }

    @Override
    public String keyInGroup() {
        return this.groupedDataListener == null ? null : this.groupedDataListener.keyInGroup();
    }

    protected boolean isFloorEmpty(Data data) {
        return data == null;
    }
}
