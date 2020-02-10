package com.kk.app.web.bljsbridge;

/**
 * @author kk
 * @datetime 2018/10/01
 * @desc 普通的URL改变会引起这个
 */
public interface OnUrlChangeListener {

    boolean onChangeUrl(String url);

    boolean onPageStarted(String url);
}
