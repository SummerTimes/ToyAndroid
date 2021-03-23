package com.kk.app.mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kk.app.mobile.utils.AppJumpUtil;


/**
 * @author kk
 * @datetime: 2020/9/1
 * @des #启动Splash
 */
public class   SplashActivity extends AppCompatActivity {

    private ImageView mImKlcw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.app_splash_avy);
        initMain();
    }

    private void initMain() {
        mImKlcw = findViewById(R.id.im_klcw);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppJumpUtil.startMainAvy(SplashActivity.this);
                finish();
            }
        },2200);
    }
}