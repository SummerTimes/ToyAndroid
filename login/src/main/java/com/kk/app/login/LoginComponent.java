package com.kk.app.login;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.kk.app.login.constant.LoginConstant;
import com.kk.app.login.util.LoginUtils;


/**
 * @author kk
 * @datetime: 2018/10/24
 * @desc:组件/我的Component
 */
public class LoginComponent implements IComponent {

    @Override
    public String getName() {
        return LoginConstant.KRY_LOGIN_COMPONENT;
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case LoginConstant.KRY_LOGIN_ACTIVITY_ACTION:
                LoginUtils.openLoginActivity(cc);
                CC.sendCCResult(cc.getCallId(), CCResult.success());
                return true;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error("actionName not specified"));
                break;
        }
        return true;
    }
}
