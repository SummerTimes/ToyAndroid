package com.kk.app.lib.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.kk.app.lib.widget.R;


/**
 * @Auther: yd
 * @datetime: 2018/10/23
 * @desc:
 */
public class LoadingProgressDialog extends Dialog {
    private static final String TAG = "LoadingProgressDialog";
    private boolean isOpenDlg;
    private Runnable backAction;
    private ImageView loadingImg;

    public void setBackAction(Runnable backAction) {
        this.backAction = backAction;
    }

    public static LoadingProgressDialog createDialog(Context context) {
        LoadingProgressDialog progressDialog = new LoadingProgressDialog(context, R.style.lwLoadingProgressStyle);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public LoadingProgressDialog(Context context, int progressDialog) {
        super(context, progressDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lw_loading_progress);
        loadingImg = findViewById(R.id.iv_loading);
        Log.i(TAG, "show");
    }

    @Override
    public void show() {
        Log.i(TAG, "show");
        isOpenDlg = true;
        if (!isShowing()) {
            super.show();
            Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.lw_rotate);
            anim.setInterpolator(new LinearInterpolator());
            loadingImg.startAnimation(anim);
        }
    }

    @Override
    public void cancel() {
        Log.i(TAG, "cancel");
        isOpenDlg = false;
        if (isShowing()) {
            super.cancel();
            loadingImg.clearAnimation();
        }
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        if (isOpenDlg && backAction != null) {
            backAction.run();
        }
        super.onBackPressed();
    }
}
