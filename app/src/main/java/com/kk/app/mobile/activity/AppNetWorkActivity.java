package com.kk.app.mobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kk.app.mobile.BuildConfig;
import com.kk.app.mobile.R;
import com.kk.app.mobile.utils.AppDeviceUtil;

/**
 * @author kk
 * @datetime 2020/3/2
 * @desc 环境切换
 */
public class AppNetWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_network_activity);
    }

    /**
     * 设置测试环境
     *
     * @param view
     */
    public void setSitNetWork(View view) {
        AppDeviceUtil.setNetWorkType(this, "sit");
    }

    /**
     * 设置预生产环境
     *
     * @param view
     */
    public void setPreNetWork(View view) {
        AppDeviceUtil.setNetWorkType(this, "pre");
    }

    /**
     * 设置生产环境
     *
     * @param view
     */
    public void setPrdNetWork(View view) {
        AppDeviceUtil.setNetWorkType(this, "prd");
    }
}