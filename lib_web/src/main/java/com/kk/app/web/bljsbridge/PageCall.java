package com.kk.app.web.bljsbridge;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc
 */
public interface PageCall {

    void onPageStarted(String url);

    void onPageFinished(String url);

}
