package com.kk.app.lib.rv.kln;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class KAdapter extends RecyclerView.Adapter<KotlinHolder> {

    private final List<Object> list;

    public KAdapter(List<Object> list) {
        this.list = list;
    }

    @Override
    public KotlinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new KotlinHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }


    @Override
    public void onBindViewHolder(KotlinHolder holder, int position) {
        KotlinFloor kotlinFloor = (KotlinFloor) list.get(position);
        kotlinFloor.onBindHolder(holder);
    }


    @Override
    public int getItemViewType(int position) {
        KotlinFloor o = (KotlinFloor) list.get(position);
        return o.viewType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
