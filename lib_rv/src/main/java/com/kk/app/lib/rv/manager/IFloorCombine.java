package com.kk.app.lib.rv.manager;


import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface IFloorCombine {

    void onRequestInflateRecyclerView(IUI ui);

    List<Object> lastFloors();

    @NonNull
    List<Object> getFloors();

    boolean getAlreadyInsert();

    void setAlreadyInsert(boolean b);

    void infoCombineDataChanged();

    int getIndex();
}
