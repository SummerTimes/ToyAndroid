package com.kk.app.webview.title;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;


import com.kk.app.web.bljsbridge.BridgeWebView;

import org.json.JSONObject;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public class WebTitleFactory {

    public static ITitle produceWebTitle(String url, Activity activity, BridgeWebView bridgeWebView) {
        Intent intent = activity.getIntent();
        String params = intent.getStringExtra("params");
        try {
            JSONObject jsonObject = new JSONObject(params);
            String address = jsonObject.optString("address");
            if (!TextUtils.isEmpty(address)) {
                return new StoreTitle(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (url.contains("eCardList")) {
            return new ECardTitle(activity);
        }
        if (url.contains("giftCardHome")) {
            return new NoShareTitle(activity);
        }
        if (url.contains("giftCardTheme")) {
            return new NoShareTitle(activity);
        }
        if (url.contains("myComment") || url.contains("myCollection")) {
            return new NoShareTitle(activity);
        }
        if (url.contains("/#/recharge")) {
            return new RechargeTitle(activity);
        }
        if (url.contains("sign/daysign")) {
            return new DaySignTitle(activity);
        }
        if (url.contains("corgLoginRequest.do")) {
            return new AnfbLoginTitle(activity);
        } else if (url.contains("flashSales") || url.contains("flashsaleproductspage")) {
            return new ShareTitle(activity);
        } else if (url.contains("viewRedPacket")) {
            return new RedShareTitle(activity);
        } else if (url.contains("/buyList")) {
            return new BuyListTitle(activity);
        } else if (isCmsUrl(url)) {
            return new CmsTitle(activity);
        } else if (url.contains("#/mybookings")) {
            return new RightMoreTitle(activity);
        }
        return new CommonTitle(activity);
    }

    private static boolean isCmsUrl(String url) {
        return url.contains("http://promotion.st.bl.com")
                || url.contains("http://promotion.ut.bl.com")
                || url.contains("http://promotion.bl.com");
    }
}
