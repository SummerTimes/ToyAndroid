package com.kk.app.lib.rv;

import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class FloorViewHolderMaker {

    private static final SparseArray<IFloorHolderFactory> FACTORIES = new SparseArray<>();

    private static void registerFloorFactory(IFloorHolderFactory factory) {
        if (factory == null) {
            return;
        }
        int viewType = factory.getViewType();
        if (viewType == 0) {
            throw new RuntimeException(factory.getClass().getName() + ".getViewType() should not be 0.");
        }
        IFloorHolderFactory oldFactory = FACTORIES.get(viewType);
        if (oldFactory != null) {
            throw new RuntimeException("more than 1 IFloorHolderFactory class with the same viewType:"
                    + viewType
                    + ", (" + factory.getClass().getName()
                    + "," + oldFactory.getClass().getName()
                    + ")"
            );
        }
        FACTORIES.put(viewType, factory);
    }

    public static BaseFloorHolder<Floor> createFloorViewHolder(ViewGroup parent, int viewType) {
        IFloorHolderFactory factory = FACTORIES.get(viewType);
        if (factory != null) {
            return factory.createFloorHolder(parent);
        }
        return null;
    }
}
