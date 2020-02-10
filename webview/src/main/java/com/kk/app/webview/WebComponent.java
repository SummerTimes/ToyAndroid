package com.kk.app.webview;

import android.text.TextUtils;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;
import com.kk.app.web.vassonic.SonicRuntimeImpl;
import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;

import org.json.JSONObject;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class WebComponent implements IComponent {


    @Override
    public String getName() {
        return "WebComponent";
    }

    @Override
    public boolean onCall(CC cc) {

        switch (cc.getActionName()) {
            case "startWeb":
                JSONObject jsonObject = cc.getParamItem("param");
                if (jsonObject == null || jsonObject.isNull("url")) {
                    CC.sendCCResult(cc.getCallId(), CCResult.error("url地址为空"));
                    return false;
                }
                //VasSonic预先加载
                try {
                    String url = jsonObject.optString("url");
                    if (!TextUtils.isEmpty(url)) {
                        if (!SonicEngine.isGetInstanceAllowed()) {
                            SonicEngine.createInstance(new SonicRuntimeImpl(cc.getContext()), new SonicConfig.Builder().build());
                            SonicEngine.getInstance().preCreateSession(url, new SonicSessionConfig.Builder().setSupportLocalServer(true).build());
                        }
                    }
                } catch (Exception ex) {
                }
                if (WebActivity.startAct(cc.getContext(), jsonObject.toString())) {
                    CC.sendCCResult(cc.getCallId(), CCResult.success(null));
                } else {
                    CC.sendCCResult(cc.getCallId(), CCResult.error("url地址为空"));
                }
                break;
            case "getRegisterFunction":
                CC.sendCCResult(cc.getCallId(), CCResult.success("managers", FunctionRegister.mFunctionManagers));
                break;
            default:
                break;
        }

        return false;
    }
}
