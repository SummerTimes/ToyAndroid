package com.kk.app.product.flutter.channel;

import androidx.fragment.app.FragmentActivity;

import com.bl.flutter.channel.AboutChannel;
import com.bl.flutter.channel.BaseChannel;
import com.bl.flutter.channel.ChannelCall;
import com.bl.flutter.channel.FlutterJumpChannel;
import com.bl.flutter.channel.TargetMethodChannel;

import java.util.ArrayList;
import java.util.List;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class ChannelImplManager {
    private static List<ChannelCall> list = new ArrayList<>();

    public ChannelImplManager() {
        list.add(new BaseChannel());
        list.add(new TargetMethodChannel());
        list.add(new AboutChannel());
        list.add(new FlutterJumpChannel());
    }

    public void call(MethodCall call, MethodChannel.Result result, FragmentActivity act) {
        for (ChannelCall channelCall : list) {
            if (channelCall.onCall(call, result, act)) {
                return;
            }
        }
        result.notImplemented();
    }
}
