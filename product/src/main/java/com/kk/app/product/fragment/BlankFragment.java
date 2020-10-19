package com.kk.app.product.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kk.app.product.R;
import com.kk.app.product.ld.LuckyDrawView;

public class BlankFragment extends Fragment {

    private String mParam;
    private View mRootView;

    private Button mStopBtn;
    private LuckyDrawView luckDrawView;


    public static BlankFragment newInstance(String param) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("param", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString("param");
        }
    }

    private void initView() {
        luckDrawView = mRootView.findViewById(R.id.luck_draw);
        mStopBtn = mRootView.findViewById(R.id.stop);
        mStopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                int end = (int) (Math.random() * 7) ;
                luckDrawView.stop(5);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.fragment_blank, container, false);
        } else {
            ViewGroup viewGroup = (ViewGroup) mRootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(mRootView);
            }
        }
        initView();
        return mRootView;
    }
}