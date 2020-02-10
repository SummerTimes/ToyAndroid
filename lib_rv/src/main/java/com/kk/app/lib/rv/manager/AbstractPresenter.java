package com.kk.app.lib.rv.manager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kk.app.lib.rv.combine.ILoadMore;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public abstract class AbstractPresenter implements INetManager, Serializable {

    protected final int mKey;
    protected final FloorManager floorManager;

    private ICombinesProvider mCombinesProvider;

    private WeakReference<FragmentActivity> mActivity;
    private List<IFloorCombine> mCombines;
    private IUI mUi;


    public AbstractPresenter(int key) {
        this.mKey = key;
        floorManager = new FloorManager();
    }

    public void bindActivity(FragmentActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    public FragmentActivity getActivity() {
        return mActivity == null ? null : mActivity.get();
    }

    @Override
    public abstract void visitNet(boolean isPullDown);

    @Override
    public void loadMore() {
        if (mCombines == null || mCombines.size() == 0 || mUi == null) {
            return;
        }
        for (IFloorCombine floorCombine : mCombines) {
            if (floorCombine instanceof ILoadMore) {
                try {
                    ((ILoadMore) floorCombine).onLoadMore(mUi);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onUIReady(IUI iui) {
        this.mUi = iui;
        mCombinesProvider = getCombinesProvider();

        if (mCombinesProvider instanceof DynamicProvider) {
            ((DynamicProvider) mCombinesProvider).onCreate(floorManager, this, mKey);
            return;
        }
        if (mCombines == null) {
            mCombines = mCombinesProvider.createCombines(mKey);
        }
        floorManager.addAll(mCombines, mKey, this);
        floorManager.onRequestInflateUi(iui);
    }


    public void infoCombineDataChanged(IFloorCombine combine) {
        floorManager.notifyCombineChange(combine);
    }

    public void info2InSert(IFloorCombine combine) {
        this.mUi.onCombineRequestInflateUI(combine);
    }


    @Deprecated
    public void notifyDataChanged(IFloorCombine combine, IUI ui) {
        floorManager.notifyDataChanged(combine, ui);
    }

    public void notifyDataChanged(IFloorCombine combine) {
        floorManager.notifyDataChanged(combine);
    }


    public final void refresh(int key, boolean isPullDown, IUI ui) {
        visitNet(isPullDown);
        onUIReady(ui);
    }

    public void onSaveInstanceState(Bundle outState) {
        floorManager.onSaveInstanceState(outState);
    }

    @NonNull
    private ICombinesProvider getCombinesProvider() {
        if (mCombinesProvider == null) {
            mCombinesProvider = provideCombinesProvider();
        }
        return mCombinesProvider;
    }

    protected List<IFloorCombine> getCombines() {
        return mCombines;
    }


    protected abstract ICombinesProvider provideCombinesProvider();

    public final @NonNull
    RecyclerView.Adapter getAdapter() {
        return floorManager.getAdapter();
    }
}
