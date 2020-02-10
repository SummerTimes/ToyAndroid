package com.kk.app.lib.rv;

import androidx.annotation.Nullable;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc 楼层信息
 */
public class Floor<DATA> {

    private Floor() {

    }

    public boolean isHead = false;

    public boolean isFake=false;

    public String TAG;
    /**
     * 楼层的UI类型
     */
    protected int viewType;
    /**
     * 楼层所需的数据
     */
    protected DATA data;

    protected IBaseEvent uiEvent;

    private IFloorRefresh floorRefresh;

    public IBaseEvent getUiEvent() {
        return uiEvent;
    }

    public Floor<DATA> setUiEvent(IBaseEvent uiEvent) {
        this.uiEvent = uiEvent;
        return this;
    }

    /**
     * 这个主意只有楼层被bind以后才可能有值，需要判空，同时想要更改楼层必须更改楼层对应的数据源
     *
     * @return
     */
    public @Nullable
    IFloorRefresh getFloorRefresh() {
        return floorRefresh;
    }

    public void setFloorRefresh(IFloorRefresh floorRefresh) {
        this.floorRefresh = floorRefresh;
    }

    /**
     * 根据data的类型自动构建Floor泛型对象
     *
     * @param viewType viewType
     * @param data     数据
     * @return Floor对象
     */
    public static <T> Floor<T> buildFloor(int viewType, T data) {
        Floor<T> floor = new Floor<>();
        floor.viewType = viewType;
        floor.data = data;
        return floor;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public DATA getData() {
        return data;
    }

    public void setData(DATA data) {
        this.data = data;
    }
}
