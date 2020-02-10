package com.kk.app.web.bljsbridge;


/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface BridgeConfig {


    String API_BRIDGE_HEADER = "ctjsbridge://api?callbackIdentifier=";

    String METHOD_BRIDGE_HEADER = "ctjsbridge://component?callbackIdentifier=";

    String TITLE_BRIDGE_HEADER = "ctjsbridge://setTitle?callbackIdentifier=";


    String DATA = "&data=";


    String API_NAME = "&apiName=";


    String TARGET_NAME = "&targetName";


    String ACTION_NAME = "&actionName=";

    String METHOD_NAME = "&methodName=";


    String SUCCESS = "success";
    String FAIL = "fail";
    String PROGRESS = "progress";

    String TOLOADJS = "CTJSBridge.js";

    String SET_TITLE = "title";


    String SPERATE = "::::";


    String DEFAULT_METHOD_NAME = "default" + "#" + "name";

}
