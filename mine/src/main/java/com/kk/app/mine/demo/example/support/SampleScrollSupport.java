package com.kk.app.mine.demo.example.support;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SampleScrollSupport {
    public interface IScrollListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    private final RecyclerView recyclerView;

    private final List<IScrollListener> scrollListeners = new ArrayList<>();

    public SampleScrollSupport(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                for (IScrollListener listener : scrollListeners) {
                    listener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                for (IScrollListener listener : scrollListeners) {
                    listener.onScrolled(recyclerView, dx, dy);
                }
            }
        });
    }

    public void register(IScrollListener scrollListener) {
        if (!scrollListeners.contains(scrollListener)) {
            scrollListeners.add(scrollListener);
        }
    }
}
