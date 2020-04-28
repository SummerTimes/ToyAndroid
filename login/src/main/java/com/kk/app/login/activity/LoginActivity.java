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
import com.kk.app.login.constant.LoginConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

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
    public void onGetData(View view) {
        String url = LoginConstant.KRY_WXARTICLE;
        try {
            NetworkHelper.queryApi(url, "",getHeader(null) , NetworkHelper.HTTP_GET, new NetworkCallback<String>() {
                @Override
                public void onSuccess(@NonNull CCResult rawResult, String convertedResult) {
                    Log.e("xp", "---GetData----------onSuccess-----" + convertedResult);
                }

                @Override
                public void onFailed(@NonNull CCResult result) {
                    Log.e("xp", "---GetData----------onFailed-----" + result.getData());
                }

                @Override
                public void onFinally(@NonNull CCResult result) {
                    Log.e("xp", "---GetData----------onFinally--" + result.getData());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试获取网络信息数据接口
     *
     * @param view
     */
    public void onCheckNet(View view) {
        onRequestLogin();
    }

    /**
     * 测试登录接口
     */
    public void onRequestLogin() {
        String url = LoginConstant.KRY_LOGIN;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", "naivetimes");
            jsonObject.put("password", "123456");
            NetworkHelper.queryApi(url, jsonObject.toString(), getHeader(null), NetworkHelper.HTTP_POST, new NetworkCallback<String>() {
                @Override
                public void onSuccess(@NonNull CCResult rawResult, String convertedResult) {
                    Log.e("xp", "-Login--onSuccess----" + convertedResult);
                }

                @Override
                public void onFailed(@NonNull CCResult result) {
                    Log.e("xp", "-Login--onFailed----" + result.getData());
                }

                @Override
                public void onFinally(@NonNull CCResult result) {
                    Log.e("xp", "-Login--onFinally----" + result.getData());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static String getHeader(JSONObject header) {
        if (header == null) {
            header = new JSONObject();
        }
        try {
            header.put("Content-Type", "application/x-www-form-urlencoded");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return header.toString();
    }
}
