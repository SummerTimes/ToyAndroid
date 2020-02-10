package com.kk.app.login.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.billy.cc.core.component.CCResult;
import com.kk.app.lib.network.NetworkCallback;
import com.kk.app.lib.network.NetworkHelper;
import com.kk.app.lib.widget.utils.LxStatusBarUtil;
import com.kk.app.login.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:
 */
public class LoginActivity extends AppCompatActivity {

    private List<String> mUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        LxStatusBarUtil.setLightMode(this);
    }


    /**
     * 测试获取网络信息数据接口
     *
     * @param view
     */
    public void onCheckNet(View view) {
        String Url = "https://www.wanandroid.com/user/login";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", "naivetimes");
            jsonObject.put("password", "123456");
            NetworkHelper.queryApi(Url, jsonObject.toString(), NetworkHelper.HTTP_POST, new NetworkCallback<String>() {
                @Override
                public void onSuccess(@NonNull CCResult rawResult, String str) {
                    Log.e("xp", "---onSuccess----" + str);
                }

                @Override
                public void onFailed(@NonNull CCResult result) {
                    Log.e("xp", "---onFailed----" + result.getData());
                }

                @Override
                public void onFinally(@NonNull CCResult result) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
