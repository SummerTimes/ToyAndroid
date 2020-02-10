package com.kk.app.lib.rv.manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.billy.android.preloader.PreLoader;
import com.billy.android.preloader.interfaces.GroupedDataListener;
import com.kk.app.lib.rv.util.FloorGroupListener;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public abstract class AbstractFloorCombine implements IFloorCombine, Serializable {

    private final List<Object> mFloors = new ArrayList<>();
    private final List<Object> mLastFloors = new ArrayList<>();

    private final int mKey;
    private boolean mHasAlreadyInsetIntoUi = false;
    private WeakReference<AbstractPresenter> mPresenter;

    public AbstractFloorCombine(int key) {
        this.mKey = key;
    }

    protected int getKey() {
        return mKey;
    }

    @Override
    public List<Object> getFloors() {
        return mFloors;
    }

    @Override
    public final void onRequestInflateRecyclerView(IUI ui) {
        if (ui == null) {
            return;
        }
        onUIReady(ui, getAlreadyInsert());
    }

    @Override
    public final List<Object> lastFloors() {
        return mLastFloors;
    }

    public final AbstractFloorCombine add(Object floor) {
        mFloors.add(floor);
        return this;
    }

    public final AbstractFloorCombine addFloor(Object floor) {
        mFloors.add(floor);
        return this;
    }

    public void bindPresenter(AbstractPresenter fragmentActivity) {
        this.mPresenter = new WeakReference<>(fragmentActivity);
    }


    public @Nullable
    AbstractPresenter getPresenter() {
        return mPresenter == null ? null : mPresenter.get();
    }

    public @Nullable
    FragmentActivity getActivity() {
        return (mPresenter == null || mPresenter.get() == null) ? null : mPresenter.get().getActivity();
    }

    @Override
    public final boolean getAlreadyInsert() {
        return mHasAlreadyInsetIntoUi;
    }

    public void info2Insert(@NonNull IUI iui) {
        iui.onCombineRequestInflateUI(this);
    }


    public void infoDataSetChanged() {
        AbstractPresenter presenter = mPresenter.get();
        if (presenter != null) {
            presenter.notifyDataChanged(this);
        }
    }


    public <Data> void mainFloorListenData(GroupedDataListener<Data> listener) {
        PreLoader.listenData(mKey, new FloorGroupListener<Data>(this, listener) {
            @Override
            protected boolean isFloorEmpty(Data data) {
                return AbstractFloorCombine.this.isFloorEmpty(data);
            }
        });
    }


    public <Data> void refrshFloorlistenData(GroupedDataListener<Data> listener) {
        PreLoader.listenData(mKey, listener);
    }

    public boolean isFloorEmpty(Object data) {
        return data == null;
    }


    @Override

    public void infoCombineDataChanged() {
        AbstractPresenter presenter = getPresenter();
        if (presenter != null) {
            presenter.infoCombineDataChanged(this);
        }
    }

    /**
     * ui请求绘制该combine提供的内容
     *
     * @param ui
     * @param alreadyInsert
     */
    protected abstract void onUIReady(IUI ui, boolean alreadyInsert);

    /**
     * 默认不设置按照添加顺序
     *
     * @return
     */
    @Override
    public int getIndex() {
        return -1;
    }

    @Override
    public final void setAlreadyInsert(boolean b) {
        this.mHasAlreadyInsetIntoUi = b;
    }
}
