package com.kk.app.lore.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.billy.cc.core.component.CC;
import com.kk.app.lore.R;
import com.kk.app.lore.constant.LoreConstant;

/**
 * @author kk
 * @datetime: 2019/08/07
 * @desc: Lore/Fragment
 */
public class LoreFragment extends Fragment {

    private View rootView;

    private String mParam;

    public static LoreFragment newInstance(String param) {
        LoreFragment fragment = new LoreFragment();
        Bundle args = new Bundle();
        args.putString(LoreConstant.KRY_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(LoreConstant.KRY_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.lore_fragment, container, false);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }
        initView();
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.tv_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CC.obtainBuilder("loreComponent")
                        .setActionName("loreActivity")
                        .addParam("param", "Lore/12345")
                        .build()
                        .callAsync();
            }
        });
    }
}
