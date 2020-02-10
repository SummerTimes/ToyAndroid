package com.kk.app.login.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.kk.app.lib.widget.BLToast;
import com.kk.app.login.activity.LoginActivity;
import com.kk.app.login.constant.LoginConstant;

/**
 * @author kk
 * @datetime: 2018/11/14
 * @desc:
 */
public class LoginUtils {

    /**
     * 跳转LoginActivity
     *
     * @param cc 参数
     */
    public static void openLoginActivity(CC cc) {
        String param = cc.getParamItem(LoginConstant.KRY_PARAM);
        if (TextUtils.isEmpty(param)) {
            BLToast.showToast(cc.getContext(), "参数不能为空");
            return;
        }
        Context context = cc.getContext();
        Intent intent = new Intent(context, LoginActivity.class);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(LoginConstant.KRY_PARAM, param);
        context.startActivity(intent);
    }

}
