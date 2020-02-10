package com.kk.app.lib.network;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.CCResult;
import com.billy.cc.core.component.IComponent;


/**
 * @author menglp
 * @since 2018/7/13 10:43
 */

public class NetworkComponent implements IComponent {
    @Override
    public String getName() {
        return "networkComponent";
    }

    @Override
    public boolean onCall(CC cc) {
        String actionName = cc.getActionName();
        switch (actionName) {
            case "getDeviceId":
                CC.sendCCResult(cc.getCallId(), CCResult.success("deviceId", NetworkConfig.deviceNum));
                break;
            default:
                CC.sendCCResult(cc.getCallId(), CCResult.error(""));
                break;
        }

        return false;
    }
}
