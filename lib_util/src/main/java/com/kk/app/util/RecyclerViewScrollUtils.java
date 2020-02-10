package com.kk.app.util;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by uis on 2017/4/14.
 */

public class RecyclerViewScrollUtils {

    public static void scroll2Top(LinearLayoutManager layoutManager, RecyclerView recyclerView) {
        if (null == layoutManager || null == recyclerView) {
            return;
        }
        int index = layoutManager.findFirstVisibleItemPosition();
        if (index > 5) {
            recyclerView.scrollToPosition(5);
        }
        recyclerView.smoothScrollToPosition(0);
    }
}
