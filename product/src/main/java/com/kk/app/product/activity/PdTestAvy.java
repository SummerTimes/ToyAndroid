package com.kk.app.product.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kk.app.product.R;
import com.kk.app.product.holder.PtFanItem;
import com.kk.app.product.holder.PtCommonItem;
import com.kk.app.product.holder.PtNullItem;
import com.kk.app.product.rv.BaseItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kk
 */
public class PdTestAvy extends AppCompatActivity {

    private List<PtCommonItem> mItems;
    public BaseItemAdapter<PtCommonItem> mCommonItemAdapter;
    public RecyclerView mCommonItemRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pd_test_avy);
        initView();
        initData();
    }


    private void initView() {
        mCommonItemAdapter = new BaseItemAdapter<>();
        mCommonItemAdapter.setItems(mItems);
        mCommonItemRv = findViewById(R.id.commonItemRv);
        mCommonItemRv.setAdapter(mCommonItemAdapter);
        mCommonItemRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        mItems = new ArrayList<>();
        mItems.add(new PtNullItem());
        for (int i = 0; i < 100; i++) {
            mItems.add(new PtFanItem("下标--" + i));
        }
        updateCommonItems(mItems);
    }

    public void updateCommonItems(List<PtCommonItem> data) {
        mCommonItemAdapter.setItems(data);
        mCommonItemAdapter.notifyDataSetChanged();
    }

    public void updateCommonItem(int position) {
        mCommonItemAdapter.notifyItemChanged(position);
    }

    public BaseItemAdapter<PtCommonItem> getCommonItemAdapter() {
        return mCommonItemAdapter;
    }

}