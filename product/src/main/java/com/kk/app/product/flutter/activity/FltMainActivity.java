package com.kk.app.product.flutter.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.billy.cc.core.component.CC;
import com.kk.app.product.R;


/**
 * @author mlp00
 */
public class FltMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flt_main);

        findViewById(R.id.tv_order_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC.obtainBuilder("FlutterContentComponent")
                        .setActionName("orderList")
                        .setContext(FltMainActivity.this)
                        .build()
                        .callAsync();
            }
        });

        findViewById(R.id.tv_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC.obtainBuilder("FlutterContentComponent")
                        .setActionName("about")
                        .setContext(FltMainActivity.this)
                        .build()
                        .callAsync();
            }
        });
    }
}
