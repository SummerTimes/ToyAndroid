package com.kk.app.product.flutter.activity;


import androidx.annotation.NonNull;

import com.kk.app.product.flutter.channel.ChannelImplManager;

import io.flutter.embedding.android.FlutterFragmentActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

/**
 */
public class FltContentActivity extends FlutterFragmentActivity {
    private ChannelImplManager manager = new ChannelImplManager();

    @Override
    public void configureFlutterEngine(FlutterEngine flutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine);
        new MethodChannel(flutterEngine.getDartExecutor(), "com.bl.flutter/base")
                .setMethodCallHandler(new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                        manager.call(call, result, FltContentActivity.this);
                    }
                });
        super.configureFlutterEngine(flutterEngine);
    }

}
